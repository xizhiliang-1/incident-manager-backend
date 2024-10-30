package com.felix.incident_manager.service;

import com.felix.incident_manager.model.Incident;

import java.util.List;

public interface IncidentService {
    Incident createIncident(Incident incident);          // 创建新的 Incident
    Incident updateIncident(Incident incident);           // 更新现有 Incident
    void deleteIncident(Long id);                         // 删除 Incident
    List<Incident> getAllIncidents();                     // 获取所有 Incident
    Incident getIncidentById(Long id);                    // 根据 ID 获取单个 Incident
}
