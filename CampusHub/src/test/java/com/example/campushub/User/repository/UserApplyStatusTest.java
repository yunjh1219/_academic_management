// package com.example.campushub.user.repository;
//
//
// import com.example.campushub.dept.domain.Dept;
// import com.example.campushub.dept.repository.DeptRepository;
// import com.example.campushub.global.config.QueryDslConfig;
// import com.example.campushub.user.domain.*;
// import com.example.campushub.user.dto.LoginUser;
// import com.example.campushub.user.repository.UserRepository;
// import com.example.campushub.user.service.UserService;
// import org.aspectj.lang.annotation.After;
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.context.annotation.Import;
//
// import static org.assertj.core.api.Assertions.*;
//
//
// @DataJpaTest
// @Import({QueryDslConfig.class, UserService.class})
// public class UserApplyStatusTest {
//
//     @Autowired
//     UserService userService;
//     @Autowired
//     UserRepository userRepository;
//     @Autowired
//     DeptRepository deptRepository;
//
//     @AfterEach
//     public void tearDown() {
//         userRepository.deleteAllInBatch();
//         deptRepository.deleteAllInBatch();
//     }
//
//     @Test
//     @DisplayName("tprtmd")
//     public void joinscholarship(){
//         //given
//
//         //when
//
//         //then
// //        assertThat()
//     }
//
//
//
//     @Test
//     @DisplayName("휴-복학 신청")
//     public void updateUserStatus(){
//         //given
//         Dept dept = deptRepository.save(createDept("컴소과"));
//         User user = userRepository.save(createUser("240011", dept ));
//         LoginUser loginUser = createLoginUser(user);
//
//         //when
//         userService.updateUserStatusApply(loginUser, user.getUserNum());
//         //then
//         assertThat(user.getStatus()).isEqualTo(Status.RETURN_PENDING);
//
//     }
//
//     private User createUser(String userNum, Dept dept) {
//         return User.builder()
//                 .userName("전영욱")
//                 .userNum(userNum)
//                 .password("1234")
//                 .email("test@aaa.com")
//                 .dept(dept)
//                 .phone("1029301923801")
//                 .role(Role.USER)
//                 .type(Type.STUDENT)
//                 .grade(Grade.FIRST_GRADE)
//                 .status(Status.BREAK)
//                 .build();
//     }
//
//     private LoginUser createLoginUser(User user) {
//         return LoginUser.builder()
//                 .userNum(user.getUserNum())
//                 .role(user.getRole())
//                 .type(user.getType())
//                 .build();
//     }
//
//     private Dept createDept(String deptName) {
//         return Dept.builder()
//                 .deptName(deptName)
//                 .build();
//     }
//
// }
