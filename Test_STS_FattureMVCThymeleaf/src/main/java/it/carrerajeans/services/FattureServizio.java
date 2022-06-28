package it.carrerajeans.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.carrerajeans.entities.Fattura;
import it.carrerajeans.excp.FatturaNonTrovataException;
import it.carrerajeans.repos.IRepoFattura;

@Service
public class FattureServizio implements IFattureService {

	@Autowired
	private IRepoFattura  irf; 
	
	public FattureServizio() {
	}

	@Override
	public Fattura salvaFattura(Fattura f) {
		return irf.save(f);
	}

	@Override
	public List<Fattura> getAllFatture() {
		return irf.findAll(); 
	}

	@Override
	public Fattura getFatturaById(Long id) {
		Optional<Fattura> o = irf.findById(id);
		if (o.isPresent())
			return o.get(); 
		else 
			throw new FatturaNonTrovataException("Fattura " + id + " non trovata."); 
	}

	@Override
	public void deleteFatturaById(Long id) {
		/*
		Optional<Fattura> o = irf.findById(id);
		if (o.isPresent())
			irf.deleteById(id);
		else 
			throw new FatturaNonTrovataException("Fattura " + id + " non trovata, impossibile eliminarla."); 
			*/
		irf.delete(getFatturaById(id));
	}

	@Override
	public void updateFattura(Fattura f) {
		irf.save(f);

	}

}
