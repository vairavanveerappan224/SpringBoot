package com.example.E_commerce.User;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecification {

    public static Specification<UserEntity> nameLike(String name) {
        return (root, query, cb) ->
                StringUtils.hasText(name)
                        ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")
                        : null;
    }

    public static Specification<UserEntity> emailLike(String email) {
        return (root, query, cb) ->
                StringUtils.hasText(email)
                        ? cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%")
                        : null;
    }

    public static Specification<UserEntity> genderLike(String gender) {
        return (root, query, cb) ->
                StringUtils.hasText(gender)
                        ? cb.equal(root.get("gender"), gender)
                        : null;
    }

    public static Specification<UserEntity> phoneLike(String phoneNo) {
        return (root, query, cb) ->
                StringUtils.hasText(phoneNo)
                        ? cb.like(root.get("phoneNo"), "%" + phoneNo + "%")
                        : null;
    }

    public static Specification<UserEntity> addressLike(String address) {
        return (root, query, cb) ->
                StringUtils.hasText(address)
                        ? cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%")
                        : null;
    }
}
