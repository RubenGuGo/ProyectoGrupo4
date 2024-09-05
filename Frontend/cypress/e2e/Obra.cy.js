describe('Obra Page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/obra')
  })

  it('Navega y carga la lista', () => {
    cy.get('.obra-container').should('exist')
    cy.get('table.custom-table tbody tr').should('have.length.greaterThan', 0)
  })

  it('Entra al formulario', () => {
    cy.get('.create-button').click()
    cy.url().should('include', '/obraForm')
    cy.get('h1').should('contain', 'Nueva Obra')
  })

  it('Crea un nuevo formulario exitosamente', () => {
    cy.get('.create-button').click()
    cy.url().should('include', '/obraForm')
    cy.get('#nombre').type('Nueva Obra')
    cy.get('#autor').type('Autor Desconocido')
    cy.get('#fecha').type('2023-01-01')
    cy.get('#localizacion').type('Museo Desconocido')
    cy.get('#descripcion').type('Descripción de la nueva obra')
    cy.get('#tipo').find('option:not([disabled])').then(options => {
      const randomIndex = Math.floor(Math.random() * options.length)
      const randomOption = options[randomIndex].text
      cy.get('#tipo').select(randomOption)
    })
    
    cy.get('.submit-button').click()
    cy.get('.confirmation-dialog .confirm-button').click()
    cy.url().should('include', '/obra')
    cy.get('table.custom-table tbody tr').should('contain', 'Nueva Obra')
  })

  it('Navega a la página de actualización', () => {
    cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Actualizar').click()
    cy.url().should('include', '/obraForm')
    cy.get('h1').should('contain', 'Modificar')
  })

  it('Actualiza una obra', () => {
    cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Actualizar').click()
    cy.url().should('include', '/obraForm')
    cy.get('#nombre').clear().type('Obra Actualizada')
    cy.get('#tipo').find('option:not([disabled])').then(options => {
      const randomIndex = Math.floor(Math.random() * options.length)
      const randomOption = options[randomIndex].text
      cy.get('#tipo').select(randomOption)
      })
    cy.get('.submit-button').click()
    cy.get('.confirmation-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
    cy.url().should('include', '/obra')
    cy.get('table.custom-table tbody tr').first().should('contain', 'Obra Actualizada')
  })

  it('Borra una obra', () => {
    cy.get('table.custom-table tbody tr').first().find('.delete-button').click()
    cy.get('.delete-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
    cy.url().should('include', '/obra')
    cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Obra borrada exitosamente')
  })
})