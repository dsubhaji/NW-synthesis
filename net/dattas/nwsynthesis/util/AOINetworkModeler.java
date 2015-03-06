package net.dattas.nwsynthesis.util;

import java.util.*;

import net.dattas.nwsynthesis.databean.*;
import net.dattas.nwsynthesis.ioformat.*;

public class AOINetworkModeler {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	/* 
	 * AOI = Arc of Interest
	 * For an individual AOI(i) = (No of team areas i is a member of) / (Total no of team areas)	
	 */
		
	// Given data
	int t = 0; // No of team areas
	int i = 0; // Total no of individuals across team areas 
	int ipt = 0; // Average no of individuals per team rounded up to nearest integer
	int tpi = 0; // Average no of teams per team individual rounded up to nearest integer
	double threshold = 0.0; // Threshold to decide whether AOI is high/low for an individual
	
	// Parameters of the model
	double c = 0.0; // Probability of connection within a team area
	double a = 0.0; // Probability of an individual to have high AOI
	double f = 0; // Fraction of team areas each high AOI individual is connected to
	
	AOINetworkModeler aoiNetworkModeler = new AOINetworkModeler();
	
	Hashtable<String,Vector<String>> teamAreas = aoiNetworkModeler.generateTeamAreas(tpi, ipt);
	
	Vector<AffiliationDataBean> baseAffiliations = aoiNetworkModeler.generateBaseAffiliations(teamAreas, c);
	
	Vector<AffiliationDataBean> augmentedAffiliations = aoiNetworkModeler.generateAugmentedAffiloations(teamAreas, a, f);
	
	Vector<AffiliationDataBean> allAffiliations = new Vector<AffiliationDataBean>();
	
	allAffiliations.addAll(baseAffiliations);
	allAffiliations.addAll(augmentedAffiliations);
	
	Vector<String> individualIDs = aoiNetworkModeler.generateIndividualIDs(t, ipt);
	
	PajekInputFormatter pif = new PajekInputFormatter();
	
	String pajekFileName = pif.formatPajekInput(allAffiliations, individualIDs, 0, "entity");
	
	}
	
	public Vector<String> generateIndividualIDs(int t, int ipt)
	{
		Vector<String> generateIndividualIDs = new Vector<String>();
		
		return generateIndividualIDs;
	}
	
	/*
	 * Generates t team areas, each of ipt individuals.
	 * Each team area is a tree of height = 1; branch = ipt
	 * Stores generated team areas in a hashtable, where key = team area name
	 * and value = vector of individuals in the corresponding team area;
	 * first element of vector is the root vertex, all following elements are leafs
	 */
	public Hashtable<String,Vector<String>> generateTeamAreas(int t, int ipt)
	{
		Hashtable<String,Vector<String>> teamAreas = new Hashtable<String,Vector<String>>();
		
		return teamAreas;
	}
	/*
	 * Generates base affiliations for t team areas, with c percent of all vertices 
	 * in each tree connecting with one another.
	 */
	public Vector<AffiliationDataBean> generateBaseAffiliations(Hashtable<String,Vector<String>> teamAreas, double c)
	{
		Vector<AffiliationDataBean> baseAffiliations = new Vector<AffiliationDataBean>();
		
		
		
		return baseAffiliations;
	}
	/*
	 * Generates affiliations added due to high AOI individuals connecting across team areas.
	 * There are a * i high AOI individuals, and each of them become contributors to
	 * f * t team areas in addition to their original team area
	 */
	public Vector<AffiliationDataBean> generateAugmentedAffiloations(Hashtable<String,Vector<String>> teamAreas, double a, double f )
	{
		Vector<AffiliationDataBean> augmentedAffiliations = new Vector<AffiliationDataBean>();
		
		return augmentedAffiliations;
	}
	

/*	
	
	public Hashtable<String, Integer> generateIndividualAffiliations(Vector<String> teams, Vector<String> individuals, int tpi)
	{
		Hashtable<String, Integer> individualAffiliations = new Hashtable<String, Integer>();
		
		return individualAffiliations;
	}
	
	public Hashtable<String, Integer> calculateHowManyTeamsEachIndividual(Hashtable<String, Integer> individualAffiliations)
	{
		Hashtable<String, Integer> howManyTeamsForIndividuals = new Hashtable<String, Integer>();
		
		return howManyTeamsForIndividuals;
	}
	
	public Hashtable<String, Integer> calculateAOIForEachIndividual(Hashtable<String, Integer> howManyTeamsEachIndividual)
	{
		Hashtable<String, Integer> aoiForIndividuals = new Hashtable<String, Integer>();
		
		return aoiForIndividuals;
	}
	
	public Vector<String> detectHighAOIIndividuals(double threshold, Hashtable<String, Integer> aoiForIndividuals)
	{
		Vector<String> detectHighAOIIndividuals = new Vector<String>();
		
		return detectHighAOIIndividuals;
	}
	
*/
}
