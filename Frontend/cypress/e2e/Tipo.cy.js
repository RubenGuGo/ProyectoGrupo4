describe('Tipo Page', () => {
    beforeEach(() => {
      cy.visit('http://localhost:5173/tipo')
    })
  
    it('should load the list of tipos', () => {
      cy.get('.tipo-container').should('exist')
      cy.get('table.custom-table tbody tr').should('have.length.greaterThan', 0)
    })
  
    it('should navigate to create tipo form', () => {
      cy.get('.create-button').click()
      cy.url().should('include', '/tipoForm')
      cy.get('h1').should('contain', 'Nuevo Tipo')
    })
  
    it('should create a new tipo', () => {
      cy.get('.create-button').click()
      cy.url().should('include', '/tipoForm')
      cy.get('#nombre').type('Nuevo Tipo')
      cy.get('#descripcion').type('Descripción del nuevo tipo')
      cy.get('.submit-button').click()
      cy.get('.confirmation-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
      cy.url().should('include', '/tipo')
      cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo creado exitosamente')
      cy.get('table.custom-table tbody tr').should('contain', 'Nuevo Tipo')
    })
  
    it('should navigate to update tipo form', () => {
      cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Actualizar/Ver').click()
      cy.url().should('include', '/tipoForm')
      cy.get('h1').should('contain', 'Modificar')
    })
  
    it('should update an existing tipo', () => {
      cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Actualizar/Ver').click()
      cy.url().should('include', '/tipoForm')
      cy.get('#nombre').clear().type('Tipo Actualizado')
      cy.get('#descripcion').clear().type('Descripción actualizada del tipo')
      cy.get('.submit-button').click()
      cy.get('.confirmation-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
      cy.url().should('include', '/tipo')
      cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo actualizado exitosamente')
      cy.get('table.custom-table tbody tr').first().should('contain', 'Tipo Actualizado')
    })
  
    it('should not delete a tipo with obras', () => {
        cy.get('table.custom-table tbody tr').find('td').eq(1).invoke('text').then((text) => {
          if (text.trim() !== '') {
            cy.get('table.custom-table tbody tr').first().find('.delete-button').click();
            cy.get('.error-dialog', { timeout: 10000 }).should('be.visible');
            cy.get('.error-dialog').should('contain.text', 'No se puede eliminar el tipo porque tiene obras asociadas.');
            cy.get('.error-dialog .error-button').click();
          }
        });
      });

    it('should delete a tipo without obras', () => {
      cy.get('table.custom-table tbody tr').contains('Nuevo Tipo').parent().find('.delete-button').click()
      cy.get('.delete-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
      cy.url().should('include', '/tipo')
      cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo eliminado exitosamente')
    })
  })