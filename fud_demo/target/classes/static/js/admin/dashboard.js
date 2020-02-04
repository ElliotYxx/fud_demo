/*
Template Name: Admin Pro Admin
Author: Wrappixel
Email: niravjoshi87@gmail.com
File: js
*/
$(function() {
    "use strict";
    // ==============================================================
    // Our Visitor
    // ==============================================================

    var chart = c3.generate({
        bindto: '#files',
        data: {
            columns: [
                ['Other', 30],
                ['媒体', 30],
                ['文档', 40],
            ],

            type: 'donut',
            onclick: function(d, i) { console.log("onclick", d, i); },
            onmouseover: function(d, i) { console.log("onmouseover", d, i); },
            onmouseout: function(d, i) { console.log("onmouseout", d, i); }
        },
        donut: {
            label: {
                show: false
            },
            title: "Files",
            width: 20,

        },

        legend: {
            hide: true
        },
        color: {
            pattern: ['#1aaee1', '#24d2b5', '#6772e5']
        }
    });

    Morris.Area({
        element: 'chart',
        data: [{
            year: '2019-01',
            upload: 5,
            download: 8
        }, {
            year: '2019-02',
            upload: 13,
            download: 10
        }, {
            year: '2019-03',
            upload: 8,
            download: 6
        }, {
            year: '2019-04',
            upload: 7,
            download: 20
        }, {
            year: '2019-05',
            upload: 18,
            download: 15
        }, {
            year: '2019-06',
            upload: 10,
            download: 10
        },
            {
                year: '2019-07',
                upload: 25,
                download: 15
            }
        ],
        xkey: 'year',
        ykeys: ['upload', 'download'],
        labels: ['上传', '下载'],
        pointSize: 0,
        fillOpacity: 0,
        pointStrokeColors: ['#20aee3', '#6972e3'],
        behaveLikeLine: true,
        gridLineColor: '#e0e0e0',
        lineWidth: 3,
        hideHover: 'auto',
        lineColors: ['#20aee3', '#6972e3'],
        resize: true

    });
});