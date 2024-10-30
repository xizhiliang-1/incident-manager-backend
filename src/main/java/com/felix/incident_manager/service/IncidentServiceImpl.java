package com.felix.incident_manager.service;

import com.felix.incident_manager.exception.IncidentNotFoundException;
import com.felix.incident_manager.model.Incident;
import com.felix.incident_manager.repository.IncidentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Cacheable("incidents")
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentServiceImpl(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @Override
    @CacheEvict(value = "incidents", allEntries = true)
    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    @Override
    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new IncidentNotFoundException("Incident not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "incidents", allEntries = true)
    public Incident updateIncident(Incident incident) {
        // 首先获取现有的 Incident
        Incident existingIncident = incidentRepository.findById(incident.getId())
                .orElseThrow(() -> new IncidentNotFoundException("Incident not found with id: " + incident.getId()));

        // update fields
        existingIncident.setTitle(incident.getTitle());
        existingIncident.setDescription(incident.getDescription());
        existingIncident.setStatus(incident.getStatus());
        existingIncident.setTimestamp(incident.getTimestamp());

        // save and return incident
        return incidentRepository.save(existingIncident);
    }

    @Override
    @CacheEvict(value = "incidents", allEntries = true)
    public void deleteIncident(Long id) {
        if (!incidentRepository.findById(id).isPresent()) {
            throw new IncidentNotFoundException("Incident not found with id: " + id);
        }
        incidentRepository.deleteById(id);
    }
}
