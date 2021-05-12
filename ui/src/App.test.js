import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Home link', () => {
  render(<App />);
  const linkElement = screen.getByText(/Home/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders Advanced link', () => {
  render(<App />);
  const linkElement = screen.getByText(/Advanced/i);
  expect(linkElement).toBeInTheDocument();
});