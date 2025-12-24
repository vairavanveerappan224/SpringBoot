package com.example.E_commerce.User;

//import com.example.E_commerce.Vendor.VendorEntity;
//import com.example.E_commerce.Vendor.VendorRepository;
//import com.example.E_commerce.Vendor.VendorService;
import static com.example.E_commerce.User.UserSpecification.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
//    private final VendorRepository vendorRepo;
//    private final VendorService vendorService;


    public Page<UserEntity> findAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public UserEntity findUserById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    public UserEntity addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String updateUser(int id, UserEntity payload) {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return "User not found";
        }
        if (payload.getName() != null) user.setName(payload.getName());
        if (payload.getEmail() != null) user.setEmail(payload.getEmail());
        if (payload.getPassword() != null)
            user.setPassword(passwordEncoder.encode(payload.getPassword()));
        if (payload.getPhoneNo() != null) user.setPhoneNo(payload.getPhoneNo());
        if (payload.getUserType() != null) user.setUserType(payload.getUserType());
        if (payload.getImageFile() != null) user.setImageFile(payload.getImageFile());
        if (payload.getAddress() != null) user.setAddress(payload.getAddress());
        if (payload.getGender() != null) user.setGender(payload.getGender());
        if (payload.getGstNo() != null) user.setGstNo(payload.getGstNo());
        userRepo.save(user);
        return "User updated successfully";
    }

    public String deleteUser(int id) {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return "null";
        }
        userRepo.deleteById(id);
        return "User deleted";
    }
    public List<UserEntity> filterUsers(
            String name,
            String email,
            String gender,
            String phoneNo,
            String address
    ) {

        Specification<UserEntity> spec = Specification
                .where(nameLike(name))
                .or(emailLike(email))
                .or(genderLike(gender))
                .or(phoneLike(phoneNo))
                .or(addressLike(address));

        return userRepo.findAll(spec);
    }
    public List<UserEntity> searchUsers(String keyword) {
        Specification<UserEntity> spec = Specification
                .where(nameLike(keyword))
                .or(emailLike(keyword))
                .or(genderLike(keyword))
                .or(phoneLike(keyword))
                .or(addressLike(keyword));

        return userRepo.findAll(spec);
    }
    public List<UserView> findByType(int usertype)
    {
        return userRepo.findByUserType(usertype);
    }
    //excel read and write in db
    public void importUsers(MultipartFile file) {

        try (InputStream is = file.getInputStream()) {

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            List<UserEntity> users = new ArrayList<>();

            for (Row row : sheet) {

                // Skip header row
                if (row.getRowNum() == 0) continue;

                UserEntity user = new UserEntity();

                user.setName(row.getCell(0).getStringCellValue());
                user.setEmail(row.getCell(1).getStringCellValue());
                user.setPassword(row.getCell(2).getStringCellValue());
                user.setPhoneNo(row.getCell(3).toString());
                user.setUserType((int) row.getCell(4).getNumericCellValue());
                user.setAddress(row.getCell(5).getStringCellValue());
                user.setGender(row.getCell(6).getStringCellValue());
                user.setGstNo((int) row.getCell(7).getNumericCellValue());

                users.add(user);
            }

            userRepo.saveAll(users);

        } catch (Exception e) {
            throw new RuntimeException("Excel upload failed", e);
        }
    }
}
//    public VendorEntity findVendor()
//    {
//        return (VendorEntity) vendorService.findAll();
//    }


