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
@Table(name = "URUN", catalog = "eticaret")
public class Urun implements java.io.Serializable {

	@SequenceGenerator(name = "generator", sequenceName = "URUN_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "URUNNUMARASI")
	@Size(max = 20)
	private String urunNo;
	
	@Column(name = "TUR")
	@Size(max = 20)
	private String tur;

	@Column(name = "MARKA")
	@Size(max = 20)
	private String marka;
	
	@Column(name ="MODEL")
	@Size(max = 20)
	private String model;
	
	@Column(name = "FIYAT")
	@Size(max = 10)
	private String fiyat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrunNo() {
		return urunNo;
	}
	
	public void setUrunNo(String urunNo) {
		this.urunNo = urunNo;
	}
	
	public String getTur() {
		return tur;
	}

	public void setTur(String tur) {
		this.tur = tur;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getFiyat() {
		return fiyat;
	}

	public void setFiyat(String fiyat) {
		this.fiyat = fiyat;
	}

}