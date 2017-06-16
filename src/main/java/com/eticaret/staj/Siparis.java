package com.eticaret.staj;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "SIPARIS", catalog = "eticaret")
public class Siparis implements java.io.Serializable {

	@SequenceGenerator(name = "generator", sequenceName = "SIPARIS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@Id
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_URUN")
	@ForeignKey(name = "FK_SIPARIS_URUN_ID")
	private Urun urun;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MUSTERI")
	@ForeignKey(name = "FK_SIPARIS_MUS_ID")
	private Musteri musteri;

	@Column(name = "SIPARISADET")
	private String siparisAdet;

	@Column(name = "SIPARISTARIH")
	@Temporal(TemporalType.TIMESTAMP)
	private Date siparisTarih;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Musteri getMusteri() {
		return musteri;
	}

	public void setMusteri(Musteri musteri) {
		this.musteri = musteri;
	}

	public Urun getUrun() {
		return urun;
	}

	public void setUrun(Urun urun) {
		this.urun = urun;
	}

	public String getSiparisAdet() {
		return siparisAdet;
	}

	public void setSiparisAdet(String siparisAdet) {
		this.siparisAdet = siparisAdet;
	}

	public Date getSiparisTarih() {
		return siparisTarih;
	}

	public void setSiparisTarih(Date siparisTarih) {
		this.siparisTarih = siparisTarih;
	}

}