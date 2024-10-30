package com.felix.incident_manager;

import com.felix.incident_manager.exception.IncidentNotFoundException;
import com.felix.incident_manager.model.Incident;
import com.felix.incident_manager.repository.IncidentRepository;
import com.felix.incident_manager.service.IncidentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IncidentServiceImplTest {

    private IncidentRepository incidentRepository;
    private IncidentServiceImpl incidentService;

    @BeforeEach
    public void setUp() {
        incidentRepository = mock(IncidentRepository.class);
        incidentService = new IncidentServiceImpl(incidentRepository);
    }

    @Test
    public void testCreateIncident() {
        Incident incident = new Incident();
        incident.setTitle("Test Incident");

        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);

        Incident created = incidentService.createIncident(incident);

        assertNotNull(created);
        assertEquals("Test Incident", created.getTitle());
        verify(incidentRepository, times(1)).save(incident);
    }

    @Test
    public void testGetIncidentById_NotFound() {
        Long id = 1L;
        when(incidentRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IncidentNotFoundException.class, () -> {
            incidentService.getIncidentById(id);
        });

        assertEquals("Incident not found with id: 1", exception.getMessage());
    }

    @Test
    public void testUpdateIncident() {
        Long id = 4L;
        Incident existingIncident = new Incident();
        existingIncident.setId(id);
        existingIncident.setDescription("Old description");
        existingIncident.setTitle("Existing Incident");

        when(incidentRepository.findById(id)).thenReturn(Optional.of(existingIncident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(existingIncident);

        Incident updatedIncident = new Incident();
        updatedIncident.setId(id);
        updatedIncident.setDescription("New description");
        updatedIncident.setTitle("Updated Incident");

        Incident result = incidentService.updateIncident(updatedIncident);

        assertNotNull(result);
        assertEquals("Updated Incident", result.getTitle());
        assertEquals("New description", result.getDescription());
    }

    @Test
    public void testDeleteIncident_NotFound() {
        Long id = 1L;
        when(incidentRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IncidentNotFoundException.class, () -> {
            incidentService.deleteIncident(id);
        });

        assertEquals("Incident not found with id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteIncident_Success() {
        Long id = 1L;
        Incident existingIncident = new Incident();
        existingIncident.setId(id);
        existingIncident.setTitle("Incident to Delete");

        when(incidentRepository.findById(id)).thenReturn(Optional.of(existingIncident));

        incidentService.deleteIncident(id);

        verify(incidentRepository, times(1)).findById(id);
        verify(incidentRepository, times(1)).deleteById(id);
    }
}
