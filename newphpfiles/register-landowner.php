	<?php
require "init.php";
$uid = $_POST["uid"];
$lname = $_POST["lname"];
$fname = $_POST["fname"];
$mname = $_POST["midname"];
$gender = $_POST["gender"];
$num = $_POST["num"];

$apartment_name = $_POST["apartment_name"];
$apartment_permit = $_POST["apartment_permit"];
$apartment_available_space = $_POST["apartment_available_space"];
$apartment_fee_per_unit = $_POST["apartment_fee_per_unit"];
$apartment_location = $_POST["apartment_location"];

$transient_name = $_POST["transient_name"];
$transient_permit = $_POST["transient_permit"];
$transient_available_space = $_POST["transient_available_space"];
$transient_fee_per_unit = $_POST["transient_fee_per_unit"];
$transient_location = $_POST["transient_location"];

$bedspace_name = $_POST["bedspace_name"];
$bedspace_permit = $_POST["bedspace_permit"];
$bedspace_available_space = $_POST["bedspace_available_space"];
$bedspace_fee_per_unit = $_POST["bedspace_fee_per_unit"];
$bedspace_location = $_POST["bedspace_location"];


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
