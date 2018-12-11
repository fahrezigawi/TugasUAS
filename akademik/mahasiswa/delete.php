<?php
	include "koneksi.php";
	
	$nim		= $_POST['nim'];
	
	class emp{}
	
	if (empty($nim)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Hapus Data";
		die(json_encode($response));
	} else {
		$query 	= mysql_query("DELETE FROM mhs WHERE nim='".$nim."'");
		
		if (query) {
			$response = new emp();
			
			$response->success = 1;
			$response->message = "Data Berhasil Di Hapus";
			die(json_encode($response));
		} else{
			$response = new emp();
			
			$response->success = 0;
			$response->message = "Data Gagal Di Hapus";
			die(json_encode($response));
		}
	}
?>