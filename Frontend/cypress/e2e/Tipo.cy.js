describe('Tipo Page', () => {
    beforeEach(() => {
      cy.visit('http://localhost:5173/tipo')
    })
  
    it('Mostrar listado Tipos', () => {
      cy.get('.tipo-container').should('exist')
      cy.get('table.custom-table tbody tr').should('have.length.greaterThan', 0)
    })
  
    it('Ver formulario de creacion Tipo', () => {
      cy.get('.create-button').click()
      cy.url().should('include', '/tipoForm')
      cy.get('h1').should('contain', 'Nuevo Tipo')
    })
  
    it('Crear Tipo', () => {
      cy.get('.create-button').click()
      cy.url().should('include', '/tipoForm')
      cy.get('#nombre').type('Nuevo Tipo')
      cy.get('#descripcion').type('Descripción del nuevo tipo')
      cy.get('.submit-button').click()
      cy.url().should('include', '/tipo')
      cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo creado exitosamente')
      cy.get('table.custom-table tbody tr').should('contain', 'Nuevo Tipo')
    })
  
    it('Ver formulario de Actualizacion Tipo', () => {
      cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Editar').click()
      cy.url().should('include', '/tipoForm')
      cy.get('h1').should('contain', 'Modificar')
    })
  
    it('Actualizar Tipo', () => {
      cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Editar').click()
      cy.url().should('include', '/tipoForm')
      cy.get('#nombre').clear().type('Tipo Actualizado')
      cy.get('#descripcion').clear().type('Descripción actualizada del tipo')
      cy.get('.submit-button').click()
      cy.get('.confirmation-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
      cy.url().should('include', '/tipo')
      cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo actualizado exitosamente')
      cy.get('table.custom-table tbody tr').first().should('contain', 'Tipo Actualizado')
    })
  

    it('Se puede borrar un Tipo sin Obras', () => {
      cy.get('table.custom-table tbody tr').contains('Nuevo Tipo').parent().find('.delete-button').click()
      cy.get('.delete-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
      cy.url().should('include', '/tipo')
      cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo eliminado exitosamente')
    })
  })