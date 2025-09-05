package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.MentorDTO;
import com.example.jobtracker_api.model.entity.Mentor;
import com.example.jobtracker_api.model.mapper.MentorMapper;
import com.example.jobtracker_api.repository.MentorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorServiceTest {

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private MentorMapper mentorMapper;

    @InjectMocks
    private MentorService mentorService;

    @Test
    void testGetAllMentors() {
        Mentor m1 = new Mentor(); m1.setMentorId(1L);
        Mentor m2 = new Mentor(); m2.setMentorId(2L);
        MentorDTO d1 = new MentorDTO(1L, null, "A", "B", "a@b", "123");
        MentorDTO d2 = new MentorDTO(2L, null, "C", "D", "c@d", "456");

        when(mentorRepository.findAll()).thenReturn(List.of(m1, m2));
        when(mentorMapper.toDTO(m1)).thenReturn(d1);
        when(mentorMapper.toDTO(m2)).thenReturn(d2);

        var res = mentorService.getAllMentors();
        assertEquals(2, res.size());
    }

    @Test
    void testGetMentorById_NotFound() {
        when(mentorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mentorService.getMentorById(1L));
    }

    @Test
    void testCreateMentor() {
        MentorDTO dto = new MentorDTO(null, null, "A", "B", "a@b", "123");
        Mentor ent = new Mentor();
        Mentor saved = new Mentor(); saved.setMentorId(10L);
        MentorDTO savedDto = new MentorDTO(10L, null, "A", "B", "a@b", "123");

        when(mentorMapper.toEntity(dto)).thenReturn(ent);
        when(mentorRepository.save(ent)).thenReturn(saved);
        when(mentorMapper.toDTO(saved)).thenReturn(savedDto);

        MentorDTO res = mentorService.createMentor(dto);
        assertEquals(10L, res.getMentorId());
    }
}
