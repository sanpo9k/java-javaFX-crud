package utils;

public class User {
 private String userId;
 private String email;
 private String username;
 private String password;
 private String phoneNum;
 private String address;
 private String gender;
 private String role;
 public User(String userId, String email, String username, String password, String phoneNum, String address,
   String gender) {
  super();
  this.userId = userId;
  this.email = email;
  this.username = username;
  this.password = password;
  this.phoneNum = phoneNum;
  this.address = address;
  this.gender = gender;
  this.role = role;
 }
 public String getUserId() {
  return userId;
 }
 public void setUserId(String userId) {
  this.userId = userId;
 }
 public String getEmail() {
  return email;
 }
 public void setEmail(String email) {
  this.email = email;
 }
 public String getUsername() {
  return username;
 }
 public void setUsername(String username) {
  this.username = username;
 }
 public String getPassword() {
  return password;
 }
 public void setPassword(String password) {
  this.password = password;
 }
 public String getPhoneNum() {
  return phoneNum;
 }
 public void setPhoneNum(String phoneNum) {
  this.phoneNum = phoneNum;
 }
 public String getAddress() {
  return address;
 }
 public void setAddress(String address) {
  this.address = address;
 }
 public String getGender() {
  return gender;
 }
 public void setGender(String gender) {
  this.gender = gender;
 }
 public String getRole() {
  return role;
 }
 public String setRole(String role) {
  return this.role = role;
 }
 
 
}