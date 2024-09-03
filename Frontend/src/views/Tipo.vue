<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const tipos = ref([]);
const router = useRouter();

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.value = response.data;
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const deleteTipo = async (id) => {
  try {
    await axios.delete(`/api/tipos/${id}`);
    fetchTipos(); // Refresh the list after deletion
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

onMounted(fetchTipos);
</script>

<template>
  <div class="tipo">
    <h1>Tipos</h1>
    <button @click="createTipo" class="create-button">Nuevo Tipo</button>
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
              <li v-for="obra in tipo.obras" :key="obra">{{ obra }}</li>
            </ul>
          </td>
          <td>{{ tipo.descripcion }}</td>
          <td>
            <button @click="updateTipo(tipo.id)">Actualizar/Ver</button>
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