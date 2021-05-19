import {render, screen, waitFor} from '@testing-library/react';
import {act} from '@testing-library/react-hooks'
import axios from 'axios';
import App from './App';

jest.mock('axios')

describe('App test', () => {

    afterEach(() => jest.resetAllMocks());

    const data = {
        data: {
            name: "api-1",
            uri: "/",
            type: "HTTP",
            ip_addresses: [
                "172.20.0.2"
            ],
            start_time: "2021-05-02T10:22:50.906984",
            end_time: "2021-05-02T10:22:50.912933",
            duration: 0,
            body: "Hello World 1",
            code: 200
        }
    };

    test(' renders Home link', async () => {
        axios.get.mockResolvedValue({data});
        act(() => {
            render(<App/>);
        });
        const linkElement = await waitFor(() => screen.getByText(/Home/i));
        expect(linkElement).toBeInTheDocument();
    });

    test(' renders Advanced link', async () => {
        axios.get.mockResolvedValue({data});
        act(() => {
            render(<App/>);
        });
        const linkElement = await waitFor(() => screen.getByText(/Advanced/i));
        expect(linkElement).toBeInTheDocument();
    });
});