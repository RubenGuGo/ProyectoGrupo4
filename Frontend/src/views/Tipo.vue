<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const tipos = ref([]);
const avisos = ref([]); // Estado para los mensajes de aviso
const router = useRouter();
const showDeleteDialog = ref(false); // Estado para la ventana emergente de eliminación
const showErrorDialog = ref(false); // Estado para la ventana emergente de error
const tipoToDelete = ref(null); // ID del tipo a eliminar

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.value = response.data;
    checkAviso(); // Verificar si hay un mensaje de aviso en localStorage
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const deleteTipo = async (id) => {
  try {
    // Verificar si el tipo tiene obras asociadas
    const tipo = tipos.value.find(t => t.id === id);
    if (tipo && tipo.obras.length > 0) {
      showErrorDialog.value = true; // Muestra la ventana emergente de error
      return;
    }
    
    tipoToDelete.value = id; // Establece el ID del tipo para eliminar
    showDeleteDialog.value = true; // Muestra la ventana emergente de eliminación
  } catch (error) {
    console.error('Error deleting tipo:', error);
  }
};

const confirmDelete = async () => {
  try {
    if (tipoToDelete.value) {
      await axios.delete(`/api/tipos/${tipoToDelete.value}`);
      fetchTipos(); // Refresca la lista después de la eliminación
      showDeleteDialog.value = false; // Oculta la ventana emergente
      tipoToDelete.value = null; // Limpia el ID del tipo a eliminar
    }
    showAviso('Tipo eliminado exitosamente'); // Actualizar mensaje de aviso
  } catch (error) {
    console.error('Error deleting tipo:', error);
  }
};

const cancelDelete = () => {
  showDeleteDialog.value = false; // Oculta la ventana emergente de eliminación
  tipoToDelete.value = null; // Limpia el ID del tipo a eliminar
};

const cancelError = () => {
  showErrorDialog.value = false; // Oculta la ventana emergente de error
};

const updateTipo = (id) => {
  router.push({ name: 'TipoForm', params: { id } });
};

const createTipo = () => {
  router.push({ name: 'TipoForm' });
};

const showAviso = (mensaje) => {
  const id = Date.now();
  avisos.value.push({ id, mensaje });
  setTimeout(() => {
    avisos.value = avisos.value.filter(aviso => aviso.id !== id);
  }, 5000); // Ocultar el mensaje después de 5 segundos
};

const checkAviso = () => {
  const mensaje = localStorage.getItem('aviso');
  if (mensaje) {
    showAviso(mensaje);
    localStorage.removeItem('aviso'); // Eliminar el mensaje de localStorage después de mostrarlo
  }
};

onMounted(fetchTipos);
</script>

<template>
  <div class="tipo-container">
    <h1 class="title">Tipos</h1>
    <button @click="createTipo" class="create-button">Crear</button>
    <div v-for="aviso in avisos" :key="aviso.id" class="modal-aviso">{{ aviso.mensaje }}</div> <!-- Mostrar mensajes de aviso -->

    <table class="custom-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Número de Obras</th>
          <th>Descripción</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tipo in tipos" :key="tipo.id">
          <td>{{ tipo.nombre }}</td>
          <td>{{ tipo.obras.length }}</td> <!-- Mostrar el número total de obras -->
          <td>{{ tipo.descripcion }}</td>
          <td>
            <button @click="updateTipo(tipo.id)" class="action-button">Ver/Actualizar</button>
            <button @click="deleteTipo(tipo.id)" class="action-button delete-button">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Diálogo de eliminación -->
    <div v-if="showDeleteDialog" class="delete-dialog">
      <div class="delete-dialog-content">
        <p>¿Estás seguro de que deseas eliminar este tipo?</p>
        <div class="delete-dialog-buttons">
          <button @click="confirmDelete" class="confirm-button">Confirmar</button>
          <button @click="cancelDelete" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>

    <!-- Diálogo de error -->
    <div v-if="showErrorDialog" class="error-dialog">
      <div class="error-dialog-content">
        <p>No se puede eliminar el tipo porque tiene obras asociadas.</p>
        <div class="error-dialog-buttons">
          <button @click="cancelError" class="error-button">Aceptar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Contenedor Principal */
.tipo-container {
  max-width: 1400px;
  margin: 40px auto;
  padding: 40px 20px;
  background-color: #1b262c;  /* Azul marino oscuro */
  border-radius: 15px;
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.3);  /* Sombra más intensa */
  text-align: center; /* Centrar el texto */
}

/* Título */
.title {
  font-size: 2.5rem;
  color: #bbe1fa;  /* Azul claro vibrante */
  margin-bottom: 30px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 2px;
  text-align: center; /* Centramos el título */
}

/* Descripción */
.description {
  text-align: center;
  color: #ecf0f1;
}

/* Botón de Crear */
.create-button {
  margin-bottom: 20px;
  padding: 14px 24px;
  background-color: #3282b8;
  color: #1b262c;
  border: none;
  cursor: pointer;
  border-radius: 10px;
  font-size: 1.2rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 12px rgba(48, 148, 214, 0.4);
}

.create-button:hover {
  background-color: #218838;
}

/* Modal de Aviso */
.modal-aviso {
  position: fixed;
  top: 20px;
  right: 20px;
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  margin-top: 10px; /* Espacio entre mensajes */
}

/* Tabla */
.custom-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: #1c3b4d;  /* Azul oscuro */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

th, td {
  padding: 16px;
  text-align: left;
  font-size: 1rem;
  border-bottom: 1px solid #34495e;
  color: #ecf0f1;  /* Texto blanco suave */
}

/* Encabezados de la tabla */
th {
  background-color: #3282b8;
  color: #ecf0f1;
  text-transform: uppercase;
  letter-spacing: 1px;
}

td {
  word-wrap: break-word; /* Asegura que las palabras largas se rompan */
  white-space: normal; /* Permite que el texto se ajuste al ancho de la celda */
  overflow-wrap: break-word; /* Asegura que las palabras largas se rompan */
  max-width: 200px; /* Ajusta este valor según sea necesario */
}

/* Filas de la tabla */
tbody tr {
  background-color: #1b262c;  /* Azul marino oscuro */
  transition: background-color 0.2s ease;
}

tbody tr:hover {
  background-color: #28527a;  /* Azul más vibrante */
}

/* Botones de acción */
.action-button {
  padding: 10px 18px;
  background-color: #0f4c75;  /* Azul más oscuro */
  color: #ecf0f1;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 12px rgba(48, 148, 214, 0.4);
}

.action-button:hover {
  background-color: #3282b8;
  transform: translateY(-2px);
}

/* Botones específicos */
.delete-button {
  background-color: #c0392b; /* Rojo para eliminar */
}

.delete-button:hover {
  background-color: #e74c3c;
}

/* Diálogo de eliminación */
.delete-dialog, .error-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 1000;
}

.delete-dialog-content, .error-dialog-content {
  background-color: #fff;
  padding: 30px;
  border-radius: 8px;
  text-align: center;
}

.delete-dialog-buttons, .error-dialog-buttons {
  margin-top: 20px;
}

.confirm-button, .cancel-button, .error-button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  font-size: 1rem;
}

.confirm-button {
  background-color: #c0392b;
  color: white;
}

.confirm-button:hover {
  background-color: #e74c3c;
}

.cancel-button, .error-button {
  background-color: #3498db;
  color: white;
}

.cancel-button:hover, .error-button:hover {
  background-color: #2980b9;
}
</style>
