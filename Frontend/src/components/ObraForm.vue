<script setup>
import { reactive, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const form = reactive({
  nombre: '',
  autor: '',
  fecha: '',
  localizacion: '',
  descripcion: '',
  tipo: null // Cambia a null para almacenar el objeto Tipo
});

const tipos = reactive([]); // Array para almacenar los tipos disponibles
const showModal = ref(false); // Estado para controlar la visibilidad del modal
const modalMessage = ref(''); // Mensaje del modal
const isEditable = ref(false); // Estado para controlar si el formulario es editable

const route = useRoute();
const router = useRouter();
const id = route.params.id;

const fetchObra = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/obras/${id}`);
      Object.assign(form, response.data);
      isEditable.value = false; // Deshabilitar edición al cargar la obra
    } catch (error) {
      console.error('Error fetching obra:', error);
    }
  } else {
    isEditable.value = true; // Habilitar edición si no hay id (crear nueva obra)
  }
};

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.push(...response.data);
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const validateForm = () => {
  if (form.nombre.length > 200) {
    modalMessage.value = 'El nombre no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.autor.length > 200) {
    modalMessage.value = 'El autor no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.fecha.length > 100) {
    modalMessage.value = 'La fecha no puede exceder los 100 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.localizacion.length > 200) {
    modalMessage.value = 'La localización no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.descripcion.length > 500) {
    modalMessage.value = 'La descripción no puede exceder los 500 caracteres.';
    showModal.value = true;
    return false;
  }
  return true;
};

const submitForm = async () => {
  if (!validateForm()) return;

  try {
    if (id) {
      await axios.put(`/api/obras/${id}`, form);
      console.log('Obra actualizada:', form);
      localStorage.setItem('aviso', 'Obra actualizada exitosamente'); // Guardar mensaje de aviso
    } else {
      await axios.post('/api/obras', form);
      console.log('Obra creada:', form);
      localStorage.setItem('aviso', 'Obra creada exitosamente'); // Guardar mensaje de aviso
    }
    router.push('/obra'); // Redirige a la lista de obras después de enviar el formulario
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

const enableEditing = () => {
  isEditable.value = true;
};

const cancel = () => {
  router.push('/obra');
};

onMounted(() => {
  fetchObra();
  fetchTipos(); // Llama a fetchTipos cuando el componente se monta
});
</script>

<template>
  <h1>{{ id ? 'Modificar ' + form.nombre : 'Nueva Obra' }}</h1>
  <form @submit.prevent="submitForm">
    <div class="form-group">
      <label for="nombre">Nombre</label>
      <input type="text" id="nombre" v-model="form.nombre" :readonly="!isEditable" required />
    </div>

    <div class="form-group">
      <label for="autor">Autor</label>
      <input type="text" id="autor" v-model="form.autor" :readonly="!isEditable" required />
    </div>

    <div class="form-group">
      <label for="fecha">Fecha</label>
      <input type="text" id="fecha" v-model="form.fecha" :readonly="!isEditable" required />
    </div>

    <div class="form-group">
      <label for="localizacion">Localización</label>
      <input type="text" id="localizacion" v-model="form.localizacion" :readonly="!isEditable" required />
    </div>

    <div class="form-group">
      <label for="descripcion">Descripción</label>
      <textarea id="descripcion" v-model="form.descripcion" :readonly="!isEditable" required></textarea>
    </div>

    <div class="form-group">
      <label for="tipo">Tipo</label>
      <select id="tipo" v-model="form.tipo" :disabled="!isEditable" required>
        <option value="" disabled>Seleccione un tipo</option>
        <option v-for="tipo in tipos" :key="tipo.id" :value="tipo">{{ tipo.nombre }}</option>
      </select>
    </div>

    <button type="submit" v-if="isEditable">{{ id ? 'Modificar' : 'Crear' }}</button>
    <button type="button" @click="enableEditing" v-if="!isEditable" class="enable-edit-button">Habilitar Edición</button>
    <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
  </form>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="showModal = false">&times;</span>
      <p>{{ modalMessage }}</p>
    </div>
  </div>
</template>

<style scoped>
.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input, textarea, select {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
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

.enable-edit-button {
  background-color: #28a745;
}

.enable-edit-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #dc3545;
}

.cancel-button:hover {
  background-color: #c82333;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 500px;
  text-align: center;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>