<script setup>
import { ref, onMounted, nextTick } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const obras = ref([]);
const avisos = ref([]); // Estado para los mensajes de aviso
const router = useRouter();

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
  try {
    await axios.delete(`/api/obras/${id}`);
    fetchObras(); // Refresh the list after deletion
    showAviso('Obra borrada exitosamente'); // Actualizar mensaje de aviso
  } catch (error) {
    console.error('Error deleting obra:', error);
  }
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
  <div class="obra">
    <h1>Bienvenido a nuestra colección de obras</h1>
    <p>Aquí puedes explorar, crear, actualizar y eliminar obras de arte</p> 
    <button @click="createObra" class="create-button">Nueva Obra</button>
    <div v-for="aviso in avisos" :key="aviso.id" class="modal-aviso">{{ aviso.mensaje }}</div> <!-- Mostrar mensajes de aviso -->
    <table>
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
            <button @click="updateObra(obra.id)">Ver/Actualizar</button>
            <button @click="deleteObra(obra.id)">Borrar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.obra {
  text-align: center;
  margin-top: 50px;
}

.create-button {
  margin-bottom: 20px;
  padding: 10px 15px;
  background-color: #28a745;
  color: white;
  border: none;
  cursor: pointer;
}

.create-button:hover {
  background-color: #218838;
}

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

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  max-width: 150px; /* Ajusta el ancho máximo según sea necesario */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

th {
  background-color: #f2f2f2;
}

td {
  word-wrap: break-word; /* Permite que el texto se envuelva */
  white-space: pre-wrap; /* Mantiene los saltos de línea y espacios en blanco */
}

button {
  margin: 0 5px;
  padding: 5px 10px;
  background-color: #007BFF;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button[type="button"] {
  background-color: #dc3545;
}

button[type="button"]:hover {
  background-color: #c82333;
}
</style>