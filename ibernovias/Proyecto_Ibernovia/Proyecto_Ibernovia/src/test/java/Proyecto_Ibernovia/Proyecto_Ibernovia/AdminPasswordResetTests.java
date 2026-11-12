package Proyecto_Ibernovia.Proyecto_Ibernovia;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Controller.AdminController;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.*;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminPasswordResetTests {
    private UsuarioRepository users;
    private MockMvc mvc;
    private Usuario admin;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setup() {
        users = mock(UsuarioRepository.class);
        JwtUtil jwt = mock(JwtUtil.class);
        admin = new Usuario();
        admin.setIsAdmin(true);
        when(jwt.validateToken("admin-token")).thenReturn(true);
        when(jwt.extractUserIdFromToken("admin-token")).thenReturn(1L);
        when(users.findById(1L)).thenReturn(Optional.of(admin));
        mvc = MockMvcBuilders.standaloneSetup(new AdminController(users,
                mock(EventoRepository.class), mock(SolicitudPresupuestoRepository.class),
                mock(PromocionRepository.class), jwt, encoder)).build();
    }

    @Test
    void savesHashThatAcceptsNewPasswordAndRejectsOldPassword() throws Exception {
        Usuario client = new Usuario();
        client.setPassword(encoder.encode("old-password"));
        when(users.findById(2L)).thenReturn(Optional.of(client));
        mvc.perform(put("/api/admin/users/2/password").header("Authorization", "Bearer admin-token")
                .contentType("application/json").content("{\"password\":\"new-password\"}"))
                .andExpect(status().isNoContent()).andExpect(content().string(""));
        verify(users).save(client);
        assertNotEquals("new-password", client.getPassword());
        assertTrue(encoder.matches("new-password", client.getPassword()));
        assertFalse(encoder.matches("old-password", client.getPassword()));
    }

    @Test
    void rejectsMissingTokenInvalidTokenAndNonAdmin() throws Exception {
        for (String token : new String[]{"", "Bearer invalid", "Bearer admin-token"}) {
            admin.setIsAdmin(false);
            mvc.perform(put("/api/admin/users/2/password").header("Authorization", token)
                    .contentType("application/json").content("{\"password\":\"new-password\"}"))
                    .andExpect(status().isUnauthorized());
        }
        verify(users, never()).save(any());
    }

    @Test
    void rejectsInvalidPasswordsAndMissingUser() throws Exception {
        for (String body : new String[]{"{}", "{\"password\":\"short\"}",
                "{\"password\":\"        \"}", "{\"password\":\"" + "é".repeat(37) + "\"}"}) {
            mvc.perform(put("/api/admin/users/2/password").header("Authorization", "Bearer admin-token")
                    .contentType("application/json").characterEncoding("UTF-8").content(body))
                    .andExpect(status().isBadRequest());
        }
        mvc.perform(put("/api/admin/users/2/password").header("Authorization", "Bearer admin-token")
                .contentType("application/json").content("{\"password\":\"new-password\"}"))
                .andExpect(status().isNotFound());
        verify(users, never()).save(any());
    }
}
