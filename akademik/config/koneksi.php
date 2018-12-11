<?php

	$server		= "localhost";
	$user		= "root";
	$password	= "";
	$database	= "akademik";
	
	$connect = mysql_connect ($server, $user, $password) or die ("Koneksi Gagal!");
	mysql_select_db($database) or die ("Database Belum Siap!");
?>