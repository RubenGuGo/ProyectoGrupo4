<script setup>
import { reactive, onMounted, ref } from 'vue';
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

const showModal = ref(false); // Estado para controlar la visibilidad del modal
const modalMessage = ref(''); // Mensaje del modal
const isEditable = ref(false); // Estado para controlar si el formulario es editable

const fetchTipo = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/tipos/${id}`);
      Object.assign(form, response.data);
      isEditable.value = false; // Deshabilitar edición al cargar el tipo
    } catch (error) {
      console.error('Error fetching tipo:', error);
    }
  } else {
    isEditable.value = true; // Habilitar edición si no hay id (crear nuevo tipo)
  }
};

const validateForm = () => {
  if (form.nombre.length > 200) {
    modalMessage.value = 'El nombre no puede exceder los 200 caracteres.';
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
      await axios.put(`/api/tipos/${id}`, form);
      console.log('Tipo actualizado:', form);
      localStorage.setItem('aviso', 'Tipo actualizado exitosamente'); // Guardar mensaje de aviso
    } else {
      await axios.post('/api/tipos', form);
      console.log('Tipo creado:', form);
      localStorage.setItem('aviso', 'Tipo creado exitosamente'); // Guardar mensaje de aviso
    }
    router.push('/tipo'); // Redirige a la lista de tipos después de enviar el formulario
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

const enableEditing = () => {
  isEditable.value = true;
};

const cancel = () => {
  router.push('/tipo');
};

onMounted(fetchTipo);
</script>

<template>
  <h1>{{ id ? 'Modificar ' + form.nombre : 'Nuevo Tipo' }}</h1>
  <form @submit.prevent="submitForm">
    <div class="form-group">
      <label for="nombre">Nombre</label>
      <input type="text" id="nombre" v-model="form.nombre" :readonly="!isEditable" required />
    </div>

    <div class="form-group">
      <label for="descripcion">Descripción</label>
      <textarea id="descripcion" v-model="form.descripcion" :readonly="!isEditable" required></textarea>
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

input, textarea {
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