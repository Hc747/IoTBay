<%@page import="au.edu.uts.wsd.model.Animal"%>
<%@page import="au.edu.uts.wsd.model.Person"%>
<%@page import="java.io.IOException"%>
<%@page import="au.edu.uts.wsd.model.Species"%>
<%@page import="au.edu.uts.wsd.model.Animals"%>
<%@page import="au.edu.uts.wsd.PetManager"%>
<%
    final PetManager manager = PetManager.getInstance(application);
%>
<div class="album py-5 bg-light">
    <div class="container">
        <% for (Person person : manager.getPeople().getPeople()) { %>
        <div class="row">
            <div class="col-md-4" id="person-<%= person.getId()%>">
                <div class="card mb-4 box-shadow">
                    <div class="card-header text-center">
                        User Profile
                    </div>
                    <div class="card-body">
                        <p class="card-text">
                            <strong>
                                <%= person.getName()%>
                            </strong>
                        </p>
                        <p class="text-muted">
                            Contact: <a class="card-link" href="mailto:<%= person.getEmail()%>">
                                <%= person.getEmail()%>
                            </a>
                        </p>
                        <%
                            final Animals pets = person.getPets();

                            final int dogs = pets.lookupAll(pet -> pet.getSpecies().equals(Species.DOG)).size();
                            final int cats = pets.lookupAll(pet -> pet.getSpecies().equals(Species.CAT)).size();
                            final int birds = pets.lookupAll(pet -> pet.getSpecies().equals(Species.BIRD)).size();

                            final int total = pets.getAnimals().size();
                        %>
                        <div class="d-flex justify-content-between align-items-center">
                            <small class="text-muted">Dogs: <%= dogs%></small>
                            <small class="text-muted">Cats: <%= cats%></small>
                            <small class="text-muted">Birds: <%= birds%></small>
                            <small class="text-muted">Total: <%= total%></small>
                        </div>
                    </div>
                    <% if (total > 0) {%>
                    <div class="card-footer">
                        <button class="btn btn-sm btn-outline-secondary" type="button" data-toggle="collapse" data-target="#pets-<%= person.getId()%>" aria-controls="pets-<%= person.getId()%>" aria-expanded="false" aria-label="Display pets-<%= person.getName()%>'s pets.">
                            Toggle
                        </button>
                    </div>
                    <% }%>
                </div> 
            </div>
        </div>
        <div class="row collapse" id="pets-<%= person.getId()%>">
            <% for (Animal animal : pets.getAnimals()) {%>
            <div class="col-md-4">
                <div class="card mb-4 box-shadow">
                    <img class="card-img-top" src="<%= animal.getImageURL()%>" width="192" height="192" alt="A picture of <%= person.getName()%>'s <%= animal.getSpecies()%> <%= animal.getName()%>."/>
                    <div class="card-body">
                        <p class="card-text">
                            <strong>
                                <%= animal.getName()%>
                            </strong>
                            <span class="text-muted">, <%= animal.getSpecies()%></span>
                        </p>
                        <small class="text-muted">
                            Microchip: <%= animal.getMicrochip()%>
                        </small>

                        <div class="d-flex justify-content-between align-items-center">
                            <a href="#person-<%= person.getId()%>">Owners Profile</a>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
        </div>      
        <% } %>
    </div>
</div>
    
<section class="jumbotron text-center bg-light">
    <div class="container">
        <h1 class="jumbotron-heading">
            The data above was rendered using JSP and Java Code.
        </h1>
    </div>
</section>