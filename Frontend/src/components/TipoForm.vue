<script setup>
import { reactive, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const form = reactive({
  nombre: '',
  descripcion: '',
  obras: []
});

const route = useRoute();
const router = useRouter();
const id = route.params.id;

const fetchTipo = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/tipos/${id}`);
      Object.assign(form, response.data);
    } catch (error) {
      console.error('Error fetching tipo:', error);
    }
  }
};

const submitForm = async () => {
  try {
    if (id) {
      await axios.put(`/api/tipos/${id}`, form);
      console.log('Tipo actualizado:', form);
    } else {
      await axios.post('/api/tipos', form);
      console.log('Tipo creado:', form);
    }
    router.push('/tipo'); // Redirige a la lista de tipos después de enviar el formulario
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

const addObra = () => {
  form.obras.push('');
};

const removeObra = (index) => {
  form.obras.splice(index, 1);
};

const cancel = () => {
  router.push('/obra');
};

onMounted(fetchTipo);
</script>

<template>
  <h1>{{ id ? 'Modificar ' + nombre : 'Nuevo Tipo' }}</h1>
  <form @submit.prevent="submitForm">
    <div class="form-group">
      <label for="nombre">Nombre</label>
      <input type="text" id="nombre" v-model="form.nombre" required />
    </div>

    <div class="form-group">
      <label for="descripcion">Descripción</label>
      <textarea id="descripcion" v-model="form.descripcion" required></textarea>
    </div>

    <div class="form-group">
      <label for="obras">Obras</label>
      <div v-for="(obra, index) in form.obras" :key="index" class="obra-item">
        <input type="text" v-model="form.obras[index]" placeholder="Nombre de la obra" required />
        <button type="button" @click="removeObra(index)">Eliminar</button>
      </div>
      <button type="button" @click="addObra">Agregar Obra</button>
    </div>

    <button type="submit">Enviar</button>
    <button type="button" @click="cancel">Cancelar</button>
  </form>
</template>

<style scoped>
.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input, textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.obra-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.obra-item input {
  flex: 1;
  margin-right: 10px;
}

button {
  padding: 10px 15px;
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