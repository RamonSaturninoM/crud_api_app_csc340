// API base URL
const API_BASE_URL = 'http://localhost:8080/bears';

// Helper function to convert Bear entity to frontend format
function bearToAnimal(bear) {
    return {
        id: bear.bearId,
        name: bear.bearName || 'Unknown',
        species: bear.species || 'Unknown species',
        type: bear.type || 'Mammal',
        age: bear.age || 0,
        habitat: bear.habitat || 'Unknown',
        description: bear.bearDescription || 'No description available',
        imageUrl: bear.imageUrl || 'https://via.placeholder.com/400x300?text=No+Image'
    };
}

// Helper function to convert frontend format to Bear entity
function animalToBear(animal) {
    const bear = {
        bearName: animal.name,
        bearDescription: animal.description,
        age: parseInt(animal.age) || 0,
        habitat: animal.habitat
    };
    
    // Add optional fields if they exist
    if (animal.species) {
        bear.species = animal.species;
    }
    if (animal.type) {
        bear.type = animal.type;
    }
    if (animal.imageUrl) {
        bear.imageUrl = animal.imageUrl;
    }
    
    return bear;
}

// Fetch all bears from API
async function fetchAllBears() {
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const bears = await response.json();
        return bears.map(bearToAnimal);
    } catch (error) {
        console.error('Error fetching bears:', error);
        return [];
    }
}

// Fetch a single bear by ID
async function fetchBearById(bearId) {
    try {
        const response = await fetch(`${API_BASE_URL}/${bearId}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const bear = await response.json();
        return bearToAnimal(bear);
    } catch (error) {
        console.error('Error fetching bear:', error);
        return null;
    }
}

// Create a new bear via POST
async function createBear(animal) {
    try {
        const bear = animalToBear(animal);
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(bear)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const createdBear = await response.json();
        return bearToAnimal(createdBear);
    } catch (error) {
        console.error('Error creating bear:', error);
        throw error;
    }
}

// Update a bear via PUT
async function updateBear(bearId, animal) {
    try {
        const bear = animalToBear(animal);
        const response = await fetch(`${API_BASE_URL}/${bearId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(bear)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const updatedBear = await response.json();
        return bearToAnimal(updatedBear);
    } catch (error) {
        console.error('Error updating bear:', error);
        throw error;
    }
}

// Delete a bear via DELETE
async function deleteBear(bearId) {
    try {
        const response = await fetch(`${API_BASE_URL}/${bearId}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return true;
    } catch (error) {
        console.error('Error deleting bear:', error);
        throw error;
    }
}

// Search bears by name
async function searchBearsByName(name) {
    try {
        const encodedName = encodeURIComponent(name);
        const response = await fetch(`${API_BASE_URL}/search/${encodedName}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const bears = await response.json();
        return bears.map(bearToAnimal);
    } catch (error) {
        console.error('Error searching bears:', error);
        return [];
    }
}

// Render animal cards
function renderCards(list) {
    const gallery = document.getElementById('gallery');
    const noResults = document.getElementById('noResults');
    if (!gallery) return; // not on index.html
    
    gallery.innerHTML = '';
    
    if (list.length === 0) {
        if (noResults) {
            noResults.classList.remove('d-none');
        }
        return;
    }
    
    if (noResults) {
        noResults.classList.add('d-none');
    }
    
    list.forEach((a) => {
        const col = document.createElement('div');
        col.className = 'col-12 col-sm-6 col-lg-4';
        
        const card = document.createElement('div');
        card.className = 'card h-100 shadow-sm border-0';
        card.setAttribute('data-name', a.name.toLowerCase());
        card.setAttribute('data-species', a.species.toLowerCase());
        card.setAttribute('data-type', a.type.toLowerCase());
        card.setAttribute('data-habitat', a.habitat.toLowerCase());
        
        // Enhanced card content with detailed information
        card.innerHTML = `
            <div class="position-relative">
                <img src="${a.imageUrl}" class="card-img-top" alt="${a.name}" style="height: 250px; object-fit: cover;" onerror="this.src='https://via.placeholder.com/400x300?text=No+Image'">
                <div class="position-absolute top-0 end-0 m-2">
                    <span class="badge bg-primary">${a.type}</span>
                </div>
            </div>
            <div class="card-body d-flex flex-column">
                <h5 class="card-title fw-bold text-primary">${a.name}</h5>
                <p class="card-text text-muted small mb-2">
                    <i class="bi bi-book me-1"></i><em>${a.species}</em>
                </p>
                <p class="card-text flex-grow-1">${a.description}</p>
                <div class="mt-auto">
                    <div class="row g-2 small text-muted">
                        <div class="col-6">
                            <i class="bi bi-calendar me-1"></i>
                            <strong>Age:</strong> ${a.age} years
                        </div>
                        <div class="col-6">
                            <i class="bi bi-geo-alt me-1"></i>
                            <strong>Habitat:</strong> ${a.habitat}
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer bg-transparent border-0">
                <div class="d-grid gap-2">
                    <button class="btn btn-outline-primary btn-sm" onclick="viewDetails(${a.id})">
                        <i class="bi bi-eye me-1"></i>View Details
                    </button>
                    <div class="btn-group btn-group-sm" role="group">
                        <button class="btn btn-outline-warning" onclick="editAnimal(${a.id})" title="Edit">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-outline-danger" onclick="deleteAnimal(${a.id})" title="Delete">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </div>
            </div>
        `;
        
        col.appendChild(card);
        gallery.appendChild(col);
    });
}

// Search functionality
async function searchAnimals(query) {
    if (!query || query.trim() === '') {
        return await fetchAllBears();
    }
    
    const searchTerm = query.toLowerCase().trim();
    const allAnimals = await fetchAllBears();
    
    return allAnimals.filter(animal => {
        return animal.name.toLowerCase().includes(searchTerm) ||
               animal.species.toLowerCase().includes(searchTerm) ||
               animal.type.toLowerCase().includes(searchTerm) ||
               animal.habitat.toLowerCase().includes(searchTerm) ||
               animal.description.toLowerCase().includes(searchTerm);
    });
}

// Update search results display
function updateSearchResults(filteredAnimals, query) {
    const searchResults = document.getElementById('searchResults');
    if (!searchResults) return;
    
    if (!query || query.trim() === '') {
        searchResults.textContent = `Showing ${filteredAnimals.length} animals`;
    } else {
        searchResults.textContent = `Found ${filteredAnimals.length} animal${filteredAnimals.length !== 1 ? 's' : ''} matching "${query}"`;
    }
}

// View details function - navigates to details page
function viewDetails(bearId) {
    window.location.href = `details.html?id=${bearId}`;
}

// Edit animal function
function editAnimal(bearId) {
    window.location.href = `new-animal-form.html?id=${bearId}`;
}

// Delete animal function
async function deleteAnimal(bearId) {
    if (!confirm('Are you sure you want to delete this animal?')) {
        return;
    }
    
    try {
        await deleteBear(bearId);
        // Reload the page to refresh the list
        const allAnimals = await fetchAllBears();
        renderCards(allAnimals);
        updateSearchResults(allAnimals, '');
        
        // Show success message
        alert('Animal deleted successfully!');
    } catch (error) {
        alert('Error deleting animal. Please try again.');
        console.error(error);
    }
}

// Initialize the page
async function initializePage() {
    const searchInput = document.getElementById('searchInput');
    const clearSearch = document.getElementById('clearSearch');
    
    if (!searchInput) return; // not on index.html
    
    // Load and display all animals initially
    const allAnimals = await fetchAllBears();
    renderCards(allAnimals);
    updateSearchResults(allAnimals, '');
    
    // Search functionality
    let searchTimeout;
    searchInput.addEventListener('input', function() {
        clearTimeout(searchTimeout);
        const query = this.value;
        searchTimeout = setTimeout(async () => {
            const filteredAnimals = await searchAnimals(query);
            renderCards(filteredAnimals);
            updateSearchResults(filteredAnimals, query);
        }, 300); // Debounce search
    });
    
    // Clear search functionality
    if (clearSearch) {
        clearSearch.addEventListener('click', async function() {
            searchInput.value = '';
            const allAnimals = await fetchAllBears();
            renderCards(allAnimals);
            updateSearchResults(allAnimals, '');
        });
    }
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', initializePage);
