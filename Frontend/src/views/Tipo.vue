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
<div class="tipo-container">
  <h1>Bienvenido a nuestra sección de tipos de obras</h1>
  <p>Aquí puedes explorar, crear, actualizar y eliminar los distintos tipos de obras de arte  </p> 
  <button @click="createTipo" class="create-button">Nuevo Tipo</button>
  <table class="custom-table">
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
            <button @click="updateTipo(tipo.id)" class="action-button">Editar</button>
            <button @click="deleteTipo(tipo.id)" class="action-button delete-button">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.tipo-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
  background-color: #e8f5e9;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.title {
  font-size: 2rem;
  color: #2e7d32;
  margin-bottom: 30px;
  font-weight: 600;
}

.create-button {
  margin-bottom: 20px;
  padding: 12px 20px;
  background-color: #43a047;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.create-button:hover {
  background-color: #388e3c;
  transform: translateY(-2px);
}

.custom-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #c8e6c9;
  padding: 15px;
  text-align: left;
  font-size: 1rem;
  max-width: 150px; /* Ajusta el ancho máximo según sea necesario */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

th {
  background-color: #66bb6a;
  color: white;
}

tbody tr:hover {
  background-color: #c8e6c9;
}

ul {
  padding-left: 20px;
  margin: 0;
}

li {
  list-style-type: disc;
  color: #2e7d32;
}

td {
  word-wrap: break-word; /* Permite que el texto se envuelva */
  white-space: pre-wrap; /* Mantiene los saltos de línea y espacios en blanco */
}

button {
  margin: 0 5px;
  padding: 10px 15px;
  background-color: #81c784;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  font-size: 0.9rem;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.action-button:hover {
  background-color: #66bb6a;
  transform: translateY(-2px);
}

.delete-button {
  background-color: #e53935;
}

.delete-button:hover {
  background-color: #d32f2f;
}
</style>
