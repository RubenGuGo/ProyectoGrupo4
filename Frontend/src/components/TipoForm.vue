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
  router.push('/tipo');
};

onMounted(fetchTipo);
</script>

<template>
  <div class="form-container">
    <h1>{{ id ? 'Modificar ' + form.nombre : 'Nuevo Tipo' }}</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" v-model="form.nombre" required />
      </div>

      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="form.descripcion" required></textarea>
      </div>

      <div class="form-actions">
        <button type="submit" class="submit-button">Enviar</button>
        <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
/* Contenedor del formulario */
.form-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
  background-color: #f7fafc;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* Título */
h1 {
  font-size: 2rem;
  color: #2d3748;
  margin-bottom: 20px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 1px;
}

/* Estilos del formulario */
.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 8px;
}

input, textarea {
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #cbd5e0;
  border-radius: 8px;
  background-color: #edf2f7;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

input:focus, textarea:focus {
  border-color: #63b3ed;
  outline: none;
}

/* Botones */
.form-actions {
  display: flex;
  justify-content: space-between;
}

button {
  padding: 12px 20px;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

/* Botón de enviar */
.submit-button {
  background-color: #48bb78;
  color: white;
  box-shadow: 0 4px 10px rgba(72, 187, 120, 0.4);
}

.submit-button:hover {
  background-color: #38a169;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(72, 187, 120, 0.6);
}

/* Botón de cancelar */
.cancel-button {
  background-color: #e53e3e;
  color: white;
  box-shadow: 0 4px 10px rgba(229, 62, 62, 0.4);
}

.cancel-button:hover {
  background-color: #c53030;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(229, 62, 62, 0.6);
}
</style>
