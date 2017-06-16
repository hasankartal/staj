package com.eticaret.staj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MUSTERI", catalog = "eticaret")
public class Musteri implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "MUSTERI_ID_SEQ")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "MUSTERINUMARASI")
	@Size(max = 20)
	private String musteriNo;
	
	@Column(name = "TCKIMLIKNUMARASI")
	@Size(max = 11)
	private String TcNo;
	
	@Column(name = " AD")
	@Size(max = 20)
	private String ad;

	@Column(name = "SOYAD")
	@Size(max = 20)
	private String soyad;

	@Column(name = "Il")
	@Size(max = 20)
	private String il;
	
	@Column(name = "Ilce")
	@Size(max = 20)
	private String ilce;
	
	@Column(name = "Mahalle")
	@Size(max = 25)
	private String mahalle;
	
	@Column(name = "TELEFONNUMARASI")
	@Size(max = 11)
	private String telNo;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMusteriNo() {
		return musteriNo;
	}
	
	public void setMusteriNo(String musteriNo) {
		this.musteriNo = musteriNo;
	}

	public String getTcNo() {
		return TcNo;
	}
	
	public void setTcNo(String TcNo) {
		this.TcNo = TcNo;
	}
	
	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	
	public String getIl() {
		return il;
	}

	public void setIl(String il) {
		this.il = il;
	}

	public String getIlce() {
		return ilce;
	}

	public void setIlce(String ilce) {
		this.ilce = ilce;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

}