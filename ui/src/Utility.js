import {useEffect, useState} from "react";
import axios from 'axios';

/**
 * @description
 * Custom hook that retrieve data from a given Rest API
 *
 * @typedef DataApiVariables
 * @type {object}
 * @property {object} data: Object fetched from the rest api
 * @property {boolean} loaded: Boolean flag to inform you if data object is loaded or not
 * @property {object} error: Object contained an error, if it happened, from the fetch
 *
 * @typedef DataApiResponse
 * @type {object}
 * @property {DataApiVariables} variables: Object fetched from the rest api
 * @property {function} setUrl: function hook to set the url to retrieve the data from
 * @property {function} getUrl: function hook to get the current set up url
 *
 *
 * @returns {DataApiResponse} response: Object containing all the variables (data, loaded, error) and hooks (setUrl, getUrl) needed
 */
export const useDataApi = () => {
    const [url, setUrl] = useState(null);
    const [error, setError] = useState(false);
    const [loaded, setLoaded] = useState(false);
    const [data, setData] = useState([]);

    // Note: the empty deps array [] means
    // this useEffect will run once
    // similar to componentDidMount()
    useEffect(() => {
        getDataApi(url)
            .then(
                (result) => {
                    setLoaded(true);
                    setError(false);
                    setData(result);
                },
                // Note: it's important to handle errors here
                // instead of a catch() block so that we don't swallow
                // exceptions from actual bugs in components.
                (error) => {
                    if(url) {
                        setLoaded(true);
                        setError(error);
                    }
                }
            )
    }, [url]);

    const getUrl = () => {return url};

    return [{ data, loaded, error }, setUrl, getUrl()];
}

/**
 * @description
 * Takes an Array<V>, and a grouping function,
 * and returns a Map of the array grouped by the grouping function.
 *
 * @param list An array of type V.
 * @param keyGetter A Function that takes the the Array type V as an input, and returns a value of type K.
 *                  K is generally intended to be a property key of V.
 *
 * @returns Map of the array grouped by the grouping function.
 */
export const groupBy = (list, keyGetter) => {
    const map = new Map();
    list.forEach((item) => {
        const key = keyGetter(item);
        const collection = map.get(key);
        if (!collection) {
            map.set(key, [item]);
        } else {
            collection.push(item);
        }
    });
    return map;
}

/**
 * @description
 * Takes an url of a REST API and it tries to retrieve data from that
 * and returns a promise for the result.
 *
 * @param string An url string.
 *
 * @returns Promise A promise that will give the data retrieved from the HTTP GET
 */
export const getDataApi = (url) => {
    return axios.get(url)
        .then(res => res.data);
}