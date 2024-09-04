<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const obras = ref([]);
const avisos = ref([]); // Estado para los mensajes de aviso
const router = useRouter();
const showDeleteDialog = ref(false); // Estado para la ventana emergente de eliminación
const obraToDelete = ref(null); // ID de la obra a eliminar

const fetchObras = async () => {
  try {
    const response = await axios.get('/api/obras');
    obras.value = response.data;
    checkAviso(); // Verificar si hay un mensaje de aviso en localStorage
  } catch (error) {
    console.error('Error fetching obras:', error);
  }
};

const deleteObra = async (id) => {
  obraToDelete.value = id; // Establece el ID de la obra para eliminar
  showDeleteDialog.value = true; // Muestra la ventana emergente
};

const confirmDelete = async () => {
  try {
    if (obraToDelete.value) {
      const obra = obras.value.find((o) => o.id === obraToDelete.value);
      if (obra && obra.obrasAsociadas && obra.obrasAsociadas.length > 0) {
        // Mostrar aviso si la obra tiene obras asociadas
        showAviso('No se puede borrar la obra porque tiene obras asociadas.');
      } else {
        await axios.delete(`/api/obras/${obraToDelete.value}`);
        fetchObras(); // Refresca la lista después de la eliminación
        showAviso('Obra borrada exitosamente'); // Actualizar mensaje de aviso
      }
      showDeleteDialog.value = false; // Oculta la ventana emergente
      obraToDelete.value = null; // Limpia el ID de la obra a eliminar
    }
  } catch (error) {
    console.error('Error deleting obra:', error);
  }
};

const cancelDelete = () => {
  showDeleteDialog.value = false; // Oculta la ventana emergente
  obraToDelete.value = null; // Limpia el ID de la obra a eliminar
};

const updateObra = async (id) => {
  router.push({ name: 'ObraForm', params: { id } });
};

const createObra = async () => {
  router.push({ name: 'ObraForm' });
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

onMounted(fetchObras);

</script>

<template>
  <div class="obra-container">
    <h1 class="title">Lista de Obras</h1>
    <p class="description">Aquí puedes explorar, crear, actualizar y eliminar obras de arte</p> 
    <button @click="createObra" class="create-button">Crear Nueva Obra</button>
    <div v-for="aviso in avisos" :key="aviso.id" class="modal-aviso">{{ aviso.mensaje }}</div> <!-- Mostrar mensajes de aviso -->
    <table class="custom-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Autor</th>
          <th>Fecha</th>
          <th>Localización</th>
          <th>Descripción</th>
          <th>Tipo</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="obra in obras" :key="obra.id">
          <td>{{ obra.nombre }}</td>
          <td>{{ obra.autor }}</td>
          <td>{{ obra.fecha }}</td>
          <td>{{ obra.localizacion }}</td>
          <td>{{ obra.descripcion }}</td>
          <td>{{ obra.tipo }}</td>
          <td>
            <button @click="updateObra(obra.id)" class="action-button">Actualizar</button>
            <button @click="deleteObra(obra.id)" class="action-button delete-button">Borrar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Ventana emergente de confirmación de eliminación -->
    <div v-if="showDeleteDialog" class="delete-dialog">
      <div class="delete-dialog-content">
        <p>¿Estás seguro de que deseas borrar esta obra?</p>
        <div class="delete-dialog-buttons">
          <button @click="confirmDelete" class="confirm-button">Confirmar</button>
          <button @click="cancelDelete" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.obra-container {
  max-width: 1400px;
  margin: 40px auto; /* Ajuste en el margen superior */
  padding: 20px;
  background-color: #1b262c;
  border-radius: 15px;
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.3);
  color: #bbe1fa;
  text-align: center; /* Cambiado de center a left para un mejor alineamiento */
}


.title {
  font-size: 2.5rem;
  color: #bbe1fa;
  margin-bottom: 30px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 2px;
}

.description {
  margin-bottom: 20px;
  font-size: 1.2rem;
  color: #bbe1fa;
}

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

.modal-aviso {
  position: fixed;
  top: 20px;
  right: 20px; /* Cambiado a la esquina superior derecha */
  transform: none; /* Eliminado el translateX para evitar centrado */
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  margin-top: 10px;
}

.custom-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: #1c3b4d;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

th, td {
  padding: 16px;
  text-align: left;
  font-size: 1rem;
  border-bottom: 1px solid #34495e;
  color: #ecf0f1;
}

th {
  background-color: #3282b8;
  color: #ecf0f1;
  text-transform: uppercase;
  letter-spacing: 1px;
}

tbody tr {
  background-color: #1b262c;
  transition: background-color 0.2s ease;
}

td {
  word-wrap: break-word;
  white-space: pre-wrap;
}

tbody tr:hover {
  background-color: #28527a;
}

.action-button {
  padding: 10px 18px;
  background-color: #0f4c75;
  color: #ecf0f1;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 12px rgba(15, 76, 117, 0.4);
}

.action-button:hover {
  background-color: #082d49;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(15, 76, 117, 0.6);
}

.delete-button {
  background-color: #e63946;
}

.delete-button:hover {
  background-color: #c92a3b;
}

.delete-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.delete-dialog-content {
  background: #1b262c;
  padding: 20px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  color: #ecf0f1;
  max-width: 400px; /* Limitar ancho del diálogo */
  margin: auto; /* Centrar horizontalmente */
  margin-top: 15%; /* Alinearlo más arriba en lugar de centrado vertical */
}

.delete-dialog-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}

.confirm-button {
  padding: 10px 18px;
  background-color: #e63946;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
}

.confirm-button:hover {
  background-color: #c92a3b;
}

.cancel-button {
  padding: 10px 18px;
  background-color: #6c757d;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
}

.cancel-button:hover {
  background-color: #5a6268;
}
</style>
