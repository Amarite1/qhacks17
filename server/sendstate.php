<?php

//todo: update these to match our final server config
$db_url = "localhost";
$db_database = "brainassist";
$db_username = "brainassist"
$db_password = "fsakj";

$mysqli = new mysqli($db_url, $db_username, $db_password, $db_database);

if($mysqli->connect_errno){ //if we couldn't connect
	die($mysqli->connect_error); //give an error json object with error message
	return; //we're done here
}

if(isset($_POST['user']) && isset($_POST['type'])){
	$datearr = getdate();
	$date = $datearr['mday']*1000000 + $datearr['month']*1000 + $datearr['year'];
	$user = mysql_real_escape_string($_POST['user']);
	$type = mysql_real_escape_string($_POST['type']);
	$statement = "INSERT INTO `statuses` (`user`,`date`,`type`) VALUES(${user},${date},${type}";

	$mysqli->query($statement);
}else{
	die("No POST Data!")
}

$mysqli->close();

?>