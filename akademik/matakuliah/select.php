<?php
	include "../config/koneksi.php";
	
	$query = mysql_query("SELECT * FROM matkul ORDER BY kode_mk ASC");
	
	$json = array();
	
	while($row = mysql_fetch_assoc($query)){
		$json[] = $row;
		}
		
	echo json_encode($json);
	
	mysql_close($connect);
?>