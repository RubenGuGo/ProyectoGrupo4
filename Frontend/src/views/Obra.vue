<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const obras = ref([]);
const router = useRouter();

const fetchObras = async () => {
  try {
    const response = await axios.get('/api/obras');
    obras.value = response.data;
  } catch (error) {
    console.error('Error fetching obras:', error);
  }
};

const deleteObra = async (id) => {
  try {
    await axios.delete(`/api/obras/${id}`);
    fetchObras(); // Refresh the list after deletion
  } catch (error) {
    console.error('Error deleting obra:', error);
  }
};

const updateObra = (id) => {
  router.push({ name: 'ObraForm', params: { id } });
};

const createObra = () => {
  router.push({ name: 'ObraForm' });
};

onMounted(fetchObras);
</script>

<template>
  <div class="obra">
    <h1>Obras</h1>
    <button @click="createObra" class="create-button">Nueva Obra</button>
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

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
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