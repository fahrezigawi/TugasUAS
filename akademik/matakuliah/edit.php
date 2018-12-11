<?php
	include "koneksi.php";
	
	$nim		= $_POST['nim'];
	
	class emp{}
	
	if (empty($nim)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Mengambil Data";
		die(json_encode($response));
	} else {
		$query 	= mysql_query("SELECT * FROM mhs WHERE nim='".$nim."'");
		$row	= mysql_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->nim = $row["nim"];
			$response->nama = $row["nama"];
			$response->alamat = $row["alamat"];
			die(json_encode($response));
		} else{
			$response = new emp();
			
			$response->success = 0;
			$response->message = "Data Gagal Di Edit";
			die(json_encode($response));
		}
	}
?>