<script setup>
import { reactive, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const form = reactive({
  nombre: '',
  autor: '',
  fecha: '',
  localizacion: '',
  descripcion: '',
  tipo: ''
});

const route = useRoute();
const router = useRouter();
const id = route.params.id;

const fetchObra = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/obras/${id}`);
      Object.assign(form, response.data);
    } catch (error) {
      console.error('Error fetching obra:', error);
    }
  }
};

const submitForm = async () => {
  try {
    if (id) {
      await axios.put(`/api/obras/${id}`, form);
      console.log('Obra actualizada:', form);
    } else {
      await axios.post('/api/obras', form);
      console.log('Obra creada:', form);
    }
    router.push( '/obra' ); // Redirige a la lista de obras después de enviar el formulario
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

const cancel = () => {
  router.push('/obra');
};

onMounted(fetchObra);
</script>

<template>
  <form @submit.prevent="submitForm">
    <div class="form-group">
      <label for="nombre">Nombre</label>
      <input type="text" id="nombre" v-model="form.nombre" required />
    </div>

    <div class="form-group">
      <label for="autor">Autor</label>
      <input type="text" id="autor" v-model="form.autor" required />
    </div>

    <div class="form-group">
      <label for="fecha">Fecha</label>
      <input type="text" id="fecha" v-model="form.fecha" required />
    </div>

    <div class="form-group">
      <label for="localizacion">Localización</label>
      <input type="text" id="localizacion" v-model="form.localizacion" required />
    </div>

    <div class="form-group">
      <label for="descripcion">Descripción</label>
      <textarea id="descripcion" v-model="form.descripcion" required></textarea>
    </div>

    <div class="form-group">
      <label for="tipo">Tipo</label>
      <input type="text" id="tipo" v-model="form.tipo" required />
    </div>

    <button type="submit">{{ id ? 'Modificar' : 'Crear' }}</button>
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
</style>