	<?php
require "init.php";
$uid = $_POST["uid"];
$lname = $_POST["lname"];
$fname = $_POST["fname"];
$mname = $_POST["midname"];
$gender = $_POST["gender"];
$num = $_POST["num"];
$permitnumber =  $_POST["permit_number"];
$unittype =  $_POST["unit_type"];
$availableunits =  $_POST["available_units"];
$feeperunit =  $_POST["fee_per_unit"];
$landaddress =  $_POST["email"];
$email = $_POST["email"];
$uname = $_POST["uname"];
$pword = $_POST["pword"];
$image = $_POST['image'];
$type = "Landowner";
$status ="Inactive";
$path = "uploads/$uid.png";
$actualpath = "http://sleepin.comli.com/$path";

$sql = "SELECT * from tbl_landowner where uname like '".$uname."';";

$result = mysqli_query($con,$sql);
$response = array();

if(mysqli_num_rows($result)>0)
{
	$code ="reg_failed";
	$message ="USER ALREADY EXIST! Registration Denied";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
}
else
{
	$sql = "INSERT INTO tbl_landowner (uid,fname,mname,lname,gender,user_type,num,email,operator_number,upload_path,address,unit_number,fee,type,status,uname,pword) VALUES ('".$uid."','".$fname."','".$mname."','".$lname."','".$gender."','".$type."','".$num."','".$email."','".$permitnumber."','".$actualpath."','".$landaddress."','".$availableunits."','".$feeperunit."','".$unittype."','".$status."','".$uname."','".$pword."');";
	$result = mysqli_query($con,$sql);
	file_put_contents($path,base64_decode($image));
	$code ="reg_success";
	$message ="Congratulations! Your account has been created.The Admin will verify your details to activate your account.You may enter your log-in credentials within an hour";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
}
mysqli_close($con);
