<?php 
 
 define('HOST','mysql6.000webhost.com');
 define('USER','a3433771_slpbspc');
 define('PASS','upang2016');
 define('DB','a3433771_uopslep');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('unable to connect to db');