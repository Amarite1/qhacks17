<?php

//todo: update these to match our final server config
$db_url = "localhost";
$db_database = "APPNAME";
$db_username = "Database_user"
$db_password = "DatabasePass";

$mysqli = new mysqli($db_url, $db_username, $db_password, $db_database);

if($mysqli->connect_errno){ //if we couldn't connect
	die("{\"status\":\"error\", \"message\":\"${$mysqli->connect_error}\"}"); //give an error json object with error message
	return; //we're done here
}

?>