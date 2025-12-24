package com.example.E_commerce.User;

import com.example.E_commerce.Pagination.PaginationInfo;
import com.example.E_commerce.User.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class UserPageResponse {
    private List<UserEntity> user;
    private PaginationInfo paginationInfo;
}
