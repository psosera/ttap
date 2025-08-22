const exercises = document.querySelectorAll('.exercise')

exercises.forEach((exercise) => {
  const solution: HTMLDivElement = exercise.querySelector('.solution')!
  if (solution) {
    const button = document.createElement('button')
    button.textContent = 'Reveal Solution'
    button.addEventListener('click', () => {
      console.log('exercise!')
      solution.style.display = 'block'
    })
    exercise.insertBefore(button, solution)
  }
})
