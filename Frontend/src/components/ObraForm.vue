<script setup>
import { reactive, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

// Estado del formulario
const form = reactive({
  nombre: '',
  descripcion: '',
  obras: []
});

// Estado para la ventana emergente
const showConfirmationDialog = ref(false);
const isConfirming = ref(false);

const route = useRoute();
const router = useRouter();
const id = route.params.id;
const readonly = route.query.readonly === 'true'; // Obtener el parámetro readonly de la URL

const showModal = ref(false); // Estado para controlar la visibilidad del modal
const modalMessage = ref(''); // Mensaje del modal
const isEditable = ref(!readonly); // Estado para controlar si el formulario es editable

const fetchTipo = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/tipos/${id}`);
      Object.assign(form, response.data);
      isEditable.value = !readonly; // Deshabilitar edición si es solo lectura
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

  if (id) {
    showConfirmationDialog.value = true;
  } else {
    await createTipo();
  }
};

const createTipo = async () => {
  try {
    await axios.post('/api/tipos', form);
    console.log('Tipo creado:', form);
    localStorage.setItem('aviso', 'Tipo creado exitosamente'); // Guardar mensaje de aviso
    router.push('/tipo'); // Redirige a la lista de tipos después de enviar el formulario
  } catch (error) {
    console.error('Error creating tipo:', error);
  }
};

const confirmSubmit = async () => {
  showConfirmationDialog.value = false;
  isConfirming.value = true;
  try {
    await axios.put(`/api/tipos/${id}`, form);
    console.log('Tipo actualizado:', form);
    localStorage.setItem('aviso', 'Tipo actualizado exitosamente'); // Guardar mensaje de aviso
    router.push('/tipo'); // Redirige a la lista de tipos después de enviar el formulario
  } catch (error) {
    console.error('Error updating tipo:', error);
  } finally {
    isConfirming.value = false;
  }
};

const enableEditing = () => {
  isEditable.value = true;
};

const cancelSubmit = () => {
  showConfirmationDialog.value = false;
};

const cancel = () => {
  router.push('/tipo');
};

const truncate = (text, length) => {
  return text.length > length ? text.substring(0, length) + '...' : text;
};

onMounted(fetchTipo);
</script>

<template>
  <div class="form-container">
    <h1>{{ id ? 'Modificar ' + form.nombre : 'Nueva Obra' }}</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" v-model="form.nombre" :readonly="readonly" required />
      </div>

      <div class="form-group">
        <label for="autor">Autor</label>
        <input type="text" id="autor" v-model="form.autor" :readonly="readonly" required />
      </div>

      <div class="form-group">
        <label for="fecha">Fecha</label>
        <input type="text" id="fecha" v-model="form.fecha" :readonly="readonly" required />
      </div>

      <div class="form-group">
        <label for="localizacion">Localización</label>
        <input type="text" id="localizacion" v-model="form.localizacion" :readonly="readonly" required />
      </div>

      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="form.descripcion" :readonly="readonly" required></textarea>
      </div>

      <div class="form-group">
        <label for="tipo">Tipo</label>
        <select id="tipo" v-model="form.tipo" :disabled="readonly" required>
          <option value="" disabled>Seleccione un tipo</option>
          <option v-for="tipo in tipos" :key="tipo.id" :value="tipo">{{ tipo.nombre }}</option>
        </select>
      </div>

      <div class="form-actions">
        <button type="submit" class="submit-button" v-if="!readonly">{{ id ? 'Guardar cambios' : 'Crear' }}</button>
        <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
      </div>
    </form>

    <!-- Ventana Emergente de Confirmación -->
    <div v-if="showConfirmationDialog" class="confirmation-dialog">
      <div class="confirmation-dialog-content">
        <p>¿Estás seguro de que deseas actualizar la obra?</p>
        <div class="confirmation-dialog-buttons">
          <button @click="confirmSubmit" class="confirm-button">Aceptar</button>
          <button @click="cancelSubmit" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="showModal = false">&times;</span>
      <p>{{ modalMessage }}</p>
    </div>
  </div>
</template>

<style scoped>
/* Contenedor del formulario */
.form-container {
  max-width: 700px;
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

input, textarea, select {
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #cbd5e0;
  border-radius: 8px;
  background-color: #edf2f7;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

input:focus, textarea:focus, select:focus {
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

/* Botón de Habilitar */
.enable-edit-button {
  background-color: #28a745;
}

.enable-edit-button:hover {
  background-color: #218838;
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

/* Ventana emergente de confirmación */
.confirmation-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.confirmation-dialog-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 400px;
}

.confirmation-dialog-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.confirm-button {
  padding: 10px 15px;
  background-color: #48bb78;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.confirm-button:hover {
  background-color: #38a169;
}

.cancel-button {
  padding: 10px 15px;
  background-color: #e53e3e;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #c53030;
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
