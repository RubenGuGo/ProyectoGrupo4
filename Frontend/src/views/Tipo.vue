<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const tipos = ref([]);
const avisos = ref([]); // Estado para los mensajes de aviso
const router = useRouter();

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
    await axios.delete(`/api/tipos/${id}`);
    fetchTipos(); // Refresh the list after deletion
    showAviso('Tipo eliminado exitosamente'); // Actualizar mensaje de aviso
  } catch (error) {
    console.error('Error deleting tipo:', error);
  }
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
  <div class="tipo">
    <h1>Bienvenido a nuestra sección de tipos de obras</h1>
    <p>Aquí puedes explorar, crear, actualizar y eliminar los distintos tipos de obras de arte</p> 
    <button @click="createTipo" class="create-button">Nuevo Tipo</button>
    <div v-for="aviso in avisos" :key="aviso.id" class="modal-aviso">{{ aviso.mensaje }}</div> <!-- Mostrar mensajes de aviso -->
    <table>
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Obras</th>
          <th>Descripción</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tipo in tipos" :key="tipo.id">
          <td>{{ tipo.nombre }}</td>
          <td>
            <ul>
              <li v-for="obra in tipo.obras" :key="obra">{{ obra.nombre }}</li>
            </ul>
          </td>
          <td>{{ tipo.descripcion }}</td>
          <td>
            <button @click="updateTipo(tipo.id)">Ver/Actualizar</button>
            <button @click="deleteTipo(tipo.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.tipo {
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