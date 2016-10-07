<?php
require "init.php";
$uname = $_POST["uname"];
$pword = $_POST["pword"];

$sql = "SELECT fname,lname,gender,level,num,email,user_type from tbluser where uname like '".$uname."' and pword like '".$pword."';";
$result = mysqli_query($con,$sql);

$response = array();

if(mysqli_num_rows($result)>0)
{
	$row = mysqli_fetch_row($result);
	$fname = $row[0];
	$lname = $row[1];
	$gender = $row[2];
	$level = $row[3];
	$num = $row[4];
	$email = $row[5];
	$type = $row[6];
	$code = "login success!";
	array_push($response,array("code"=>$code,"fname"=>$fname,"lname"=>$lname,"gender"=>$gender ,"level"=>$level,"num"=>$num,"email"=>$email,"user_type"=>$type));
	echo json_encode($response);
}
else
{
	$code = "login_failed";
	$message = "User not found / Account still not verified... Please try again";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
}
mysqli_close($con);
?>
