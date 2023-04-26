
INSERT INTO `courses` VALUES (1,'Master Spring Service','2 Hours','Spring Service',1),(2,'Introduction to JPA','4 Hours','Spring Data JPA',2);

INSERT INTO `enrolled_in` VALUES (1,1),(2,2);

INSERT INTO `instructors` VALUES (1,'instructor1FN','instructor1LN','Experienced instructor',3),(2,'instructor2FN','instructor2LN','Senior instructor',4);

INSERT INTO `roles` VALUES (1,'Admin'),(2,'Instructor'),(3,'Student');

INSERT INTO `students` VALUES (1,'student1FN','student1LN','beginner',5),(2,'student2FN','student2LN','master degree',6);

INSERT INTO `user_role` VALUES (1,1),(1,2),(2,3),(3,2),(4,2),(5,3),(6,3);

INSERT INTO `users` VALUES (1,'user1@gmail.com','pass1'),(2,'user2@gmail.com','pass2'),(3,'instructorUser1@gmail.com','pass1'),(4,'instructorUser2@gmail.com','pass2'),(5,'stdUser1@gmail.com','pass1'),(6,'stdUser2@gmail.com','pass2');
