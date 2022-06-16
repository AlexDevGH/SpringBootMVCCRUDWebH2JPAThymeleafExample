package it.carrerajeans.services;

import java.util.List;

import it.carrerajeans.entities.Fattura;


public interface IFattureService {

	public Fattura salvaFattura(Fattura f);
	public List<Fattura> getAllFatture();
	public Fattura getFatturaById(Long id);
	public void deleteFatturaById(Long Id);
	public void updateFattura(Fattura f);
	
}
