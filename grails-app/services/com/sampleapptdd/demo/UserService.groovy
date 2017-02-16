package com.sampleapptdd.demo

import com.sampleapptdd.demo.dto.UserDto
import com.sampleapptdd.demo.type.Status
import com.sampleapptdd.demo.type.RoleAuthority
import com.sampleapptdd.demo.exception.ResourceNotFoundException
import com.sampleapptdd.demo.common.AbstractValidateAndSaveService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.apache.commons.validator.routines.EmailValidator
import grails.transaction.Transactional

@Transactional
class UserService extends AbstractValidateAndSaveService {

    public User update(User user, UserDto userDto) {
        validateUpdate(userDto)
        if(hasChanges(user, userDto)) {
            validateAndSave(user)
        }

        return user
    }

    public User fetchUserById(UUID Id) {
        return User.findById(Id)
    }

    public User fetchByEmailOrUserName(String username) {
        User user
        if(EmailValidator.getInstance().isValid(username)) {
            user = User.findByEmailAddressAndStatus(username, Status.ACTIVE)
        } else {
            user = User.findByUsernameAndStatus(username, Status.ACTIVE)
        }

        if(!user) {
            throw new ResourceNotFoundException()
        }

        return user
    }

    public void setCurrentUser(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null)
        )
    }

    public User fetchCurrentUser() {
        def loggedInUser = SecurityContextHolder.getContext()?.getAuthentication()?.getPrincipal()
        if (loggedInUser instanceof User) {
            loggedInUser.refresh()
            return loggedInUser
        } else {
            return null
        }
    }

    public User verifyEmailAddress(User user) {
        user.dateVerified = new Date()
        validateAndSave(user)

        return user
    }

    public User updatePassword(User user, String newPassword) {
        user.password = newPassword
        validateAndSave(user)

        return user
    }

    public List<User> fetchAllUsersByRoles(List<RoleAuthority> roleAuthorities) {
        return User.createCriteria().list {
            roles {
                'in'('authority', roleAuthorities)
            }
        }
    }

    public User constructUser(UserDto userDto) {
        User user = new User(
                firstName: userDto.firstName,
                middleName: userDto.middleName,
                lastName: userDto.lastName,
                suffix: userDto.suffix,
                username: userDto.username,
                emailAddress: userDto.emailAddress,
                password: userDto.password,
                verified: true,
                dateVerified: new Date(),
                roles: userDto.roles
        )

        return user
    }

    /**
     * Private method below
     * */
    private void validateUpdate(UserDto userDto) {
        if(!userDto) {
            throw new ResourceNotFoundException()
        }
    }

    private boolean hasChanges(User user, UserDto userDto) {
        boolean hasChanges = false
        if(userDto.firstName && user.firstName != userDto.firstName) {
            hasChanges = true
            user.firstName = userDto.firstName
        }

        if(userDto.middleName && user.middleName != userDto.middleName) {
            hasChanges = true
            user.middleName = userDto.middleName
        }

        if(userDto.lastName && user.lastName != userDto.lastName) {
            hasChanges = true
            user.lastName = userDto.lastName
        }

        if(userDto.suffix && user.suffix != userDto.suffix) {
            hasChanges = true
            user.suffix = userDto.suffix
        }

        if(userDto.username && user.username != userDto.username) {
            hasChanges = true
            user.username = userDto.username
        }

        if(userDto.emailAddress && user.emailAddress != userDto.emailAddress) {
            hasChanges = true
            user.emailAddress = userDto.emailAddress
        }

        return hasChanges
    }
}
