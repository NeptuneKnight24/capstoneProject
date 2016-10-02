<?php
require "init.php";
$uid = $_POST["id"];
$lname = $_POST["lname"];
$fname = $_POST["fname"];
$gender = $_POST["gender"];
$level = $_POST["level"];
$num = $_POST["num"];
$uname = $_POST["uname"];
$email = $_POST["email"];
$pword = $_POST["pword"];
$type = "Tenant";


$sql = "select * from tbluser where email like '".$email."'and uname like '".$uname."';";

$result = mysqli_query($con,$sql);
$response = array();

if(mysqli_num_rows($result)>0)
{
	$code ="reg_failed";
	$message ="User already existing";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
}
else
{
	$sql = "INSERT INTO tbluser (uid,lname,fname,gender,level,num,email,uname,pword,user_type) VALUES ('".$uid."','".$lname."','".$fname."','".$gender."','".$level."','".$num."','".$email."','".$uname."','".$pword."','".$type."');";
	$result = mysqli_query($con,$sql);
	$code ="reg_success";
	$message ="Thank you for registering!";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
}
mysqli_close($con);
