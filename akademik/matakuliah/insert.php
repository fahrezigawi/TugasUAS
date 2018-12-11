<?php
	include "koneksi.php";
	
	$nama			= $_POST['nama'];
	$alamat			= $_POST['alamat'];
	
	class emp{}
	
	if (empty($nama) || empty($alamat)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Ga Boleh Kosong";
		die(json_encode($response));
	} else {
		$query = mysql_query("INSERT INTO mhs (nim,nama,alamat)
		VALUES(0,'".$nama."','".$alamat."')");
		
		if ($query) {
			$response = new emp();
			
			$response->success = 1;
			$response->message = "Data Berhasil Di Simpan";
			die(json_encode($response));
		} else {
			$response = new emp();
			
			$response->success = 0;
			$response->message = "Data Gagal Di Simpan";
			die(json_encode($response));
		}
	}
?>