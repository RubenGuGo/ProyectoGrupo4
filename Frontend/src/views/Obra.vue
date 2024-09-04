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
<div class="obra-container">
    <h1>Bienvenido a nuestra colección de obras</h1>
    <p>Aquí puedes explorar, crear, actualizar y eliminar obras de arte</p> 
    <button @click="createObra" class="create-button">Nueva Obra</button>
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
            <button @click="updateObra(obra.id)" class="action-button">Editar</button>
            <button @click="deleteObra(obra.id)" class="action-button delete-button">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
/* Contenedor Principal */
.obra-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 40px 20px;
  background-color: #f0f7f4;
  border-radius: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* Título */
.title {
  font-size: 2.5rem;
  color: #2d6a4f;
  margin-bottom: 30px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 2px;
}

/* Botón de Crear */
.create-button {
  margin-bottom: 20px;
  padding: 14px 24px;
  background-color: #52b788;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 10px;
  font-size: 1.2rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 10px rgba(82, 183, 136, 0.4);
}

.create-button:hover {
  background-color: #40916c;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(82, 183, 136, 0.6);
}

/* Tabla */
.custom-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

th, td {
  border: 1px solid #ddd;
  max-width: 150px; /* Ajusta el ancho máximo según sea necesario */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: 16px;
  text-align: left;
  font-size: 1rem;
  border-bottom: 1px solid #e2e8f0;
}

/* Encabezados de la tabla */
th {
  background-color: #52b788;
  color: white;
  text-transform: uppercase;
  letter-spacing: 1px;
}

td {
  word-wrap: break-word; /* Permite que el texto se envuelva */
  white-space: pre-wrap; /* Mantiene los saltos de línea y espacios en blanco */
}

button {
  margin: 0 5px;
  padding: 5px 10px;
  background-color: #007BFF;
}
/* Filas de la tabla */
tbody tr {
  background-color: white;
  transition: background-color 0.2s ease;
}

tbody tr:hover {
  background-color: #e9f7ef;
}

/* Botones de acción */
.action-button {
  padding: 10px 18px;
  background-color: #2d6a4f;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 10px rgba(45, 106, 79, 0.4);
}

.action-button:hover {
  background-color: #245d42;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(45, 106, 79, 0.6);
}

.delete-button {
  background-color: #e63946;
}

.delete-button:hover {
  background-color: #d62839;
}
</style>
