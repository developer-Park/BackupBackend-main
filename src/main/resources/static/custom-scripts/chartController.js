/*=========================================================================================
    File Name: dashboard-analytics.js
    Description: dashboard analytics page content with Apexchart Examples
    ----------------------------------------------------------------------------------------
    Item Name: Vuexy  - Vuejs, HTML & Laravel Admin Dashboard Template
    Author: PIXINVENT
    Author URL: http://www.themeforest.net/user/pixinvent
==========================================================================================*/

$(window).on('load', function () {
    'use strict';

    var $avgSessionStrokeColor2 = '#ebf0f7';
    var $textHeadingColor = '#5e5873';
    var $white = '#fff';
    var $strokeColor = '#ebe9f1';

    var $gainedChart = document.querySelector('#digitalAssets-chart');
    var $orderChart = document.querySelector('#projects-chart');
    var $avgSessionsChart = document.querySelector('#projectManagers-chart');
    var $supportTrackerChart = document.querySelector('#supportTickets-chart');
    var $salesVisitChart = document.querySelector('#sales-visit-chart');

    var gainedChartOptions;
    var orderChartOptions;
    var avgSessionsChartOptions;
    var supportTrackerChartOptions;
    var salesVisitChartOptions;

    var gainedChart;
    var orderChart;
    var avgSessionsChart;
    var supportTrackerChart;
    var salesVisitChart;
    var isRtl = $('html').attr('data-textdirection') === 'rtl';

    // On load Toast
    setTimeout(function () {
      toastr['success'](
        'You have successfully logged in to Vuexy. Now you can start to explore!',
        '👋 Welcome John Doe!',
        {
          closeButton: true,
          tapToDismiss: false,
          rtl: isRtl
        }
      );
    }, 2000);

    // Subscribed Gained Chart
    // ----------------------------------

    gainedChartOptions = {
      chart: {
        height: 100,
        type: 'area',
        toolbar: {
          show: false
        },
        sparkline: {
          enabled: true
        },
        grid: {
          show: false,
          padding: {
            left: 0,
            right: 0
          }
        }
      },
      colors: [window.colors.solid.primary],
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'smooth',
        width: 2.5
      },
      fill: {
        type: 'gradient',
        gradient: {
          shadeIntensity: 0.9,
          opacityFrom: 0.7,
          opacityTo: 0.5,
          stops: [0, 80, 100]
        }
      },
      series: [
        {
          name: 'Subscribers',
          data: [28, 40, 36, 52, 38, 60, 55]
        }
      ],
      xaxis: {
        labels: {
          show: false
        },
        axisBorder: {
          show: false
        }
      },
      yaxis: [
        {
          y: 0,
          offsetX: 0,
          offsetY: 0,
          padding: { left: 0, right: 0 }
        }
      ],
      tooltip: {
        x: { show: false }
      }
    };
    gainedChart = new ApexCharts($gainedChart, gainedChartOptions);
    gainedChart.render();

    // Order Received Chart
    // ----------------------------------

    orderChartOptions = {
      chart: {
        height: 100,
        type: 'area',
        toolbar: {
          show: false
        },
        sparkline: {
          enabled: true
        },
        grid: {
          show: false,
          padding: {
            left: 0,
            right: 0
          }
        }
      },
      colors: [window.colors.solid.warning],
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'smooth',
        width: 2.5
      },
      fill: {
        type: 'gradient',
        gradient: {
          shadeIntensity: 0.9,
          opacityFrom: 0.7,
          opacityTo: 0.5,
          stops: [0, 80, 100]
        }
      },
      series: [
        {
          name: 'Orders',
          data: [10, 15, 8, 15, 7, 12, 8]
        }
      ],
      xaxis: {
        labels: {
          show: false
        },
        axisBorder: {
          show: false
        }
      },
      yaxis: [
        {
          y: 0,
          offsetX: 0,
          offsetY: 0,
          padding: { left: 0, right: 0 }
        }
      ],
      tooltip: {
        x: { show: false }
      }
    };
    orderChart = new ApexCharts($orderChart, orderChartOptions);
    orderChart.render();

    // Average Session Chart
    // ----------------------------------
    avgSessionsChartOptions = {
        chart: {
          height: 100,
          type: 'area',
          toolbar: {
            show: false
          },
          sparkline: {
            enabled: true
          },
          grid: {
            show: false,
            padding: {
              left: 0,
              right: 0
            }
          }
        },
        colors: [window.colors.solid.success],
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth',
          width: 2.5
        },
        fill: {
          type: 'gradient',
          gradient: {
            shadeIntensity: 0.9,
            opacityFrom: 0.7,
            opacityTo: 0.5,
            stops: [0, 80, 100]
          }
        },
        series: [
          {
            name: 'Orders',
            data: [21, 6, 14, 7, 22, 20, 23]
          }
        ],
        xaxis: {
          labels: {
            show: false
          },
          axisBorder: {
            show: false
          }
        },
        yaxis: [
          {
            y: 0,
            offsetX: 0,
            offsetY: 0,
            padding: { left: 0, right: 0 }
          }
        ],
        tooltip: {
          x: { show: false }
        }
      };
    avgSessionsChart = new ApexCharts($avgSessionsChart, avgSessionsChartOptions);
    avgSessionsChart.render();

    // Support Tracker Chart
    // -----------------------------
    supportTrackerChartOptions = {
        chart: {
          height: 100,
          type: 'area',
          toolbar: {
            show: false
          },
          sparkline: {
            enabled: true
          },
          grid: {
            show: false,
            padding: {
              left: 0,
              right: 0
            }
          }
        },
        colors: [window.colors.solid.info],
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth',
          width: 2.5
        },
        fill: {
          type: 'gradient',
          gradient: {
            shadeIntensity: 0.9,
            opacityFrom: 0.7,
            opacityTo: 0.5,
            stops: [0, 80, 100]
          }
        },
        series: [
          {
            name: 'Orders',
            data: [5, 10, 3, 13, 6, 10]
          }
        ],
        xaxis: {
          labels: {
            show: false
          },
          axisBorder: {
            show: false
          }
        },
        yaxis: [
          {
            y: 0,
            offsetX: 0,
            offsetY: 0,
            padding: { left: 0, right: 0 }
          }
        ],
        tooltip: {
          x: { show: false }
        }
    };
    supportTrackerChart = new ApexCharts($supportTrackerChart, supportTrackerChartOptions);
    supportTrackerChart.render();

    // Sales Chart
    // -----------------------------
    salesVisitChartOptions = {
      chart: {
        height: 300,
        type: 'radar',
        dropShadow: {
          enabled: true,
          blur: 8,
          left: 1,
          top: 1,
          opacity: 0.2
        },
        toolbar: {
          show: false
        },
        offsetY: 5
      },
      series: [
        {
          name: 'Sales',
          data: [90, 50, 86, 40, 100, 20]
        },
        {
          name: 'Visit',
          data: [70, 75, 70, 76, 20, 85]
        }
      ],
      stroke: {
        width: 0
      },
      colors: [window.colors.solid.primary, window.colors.solid.info],
      plotOptions: {
        radar: {
          polygons: {
            strokeColors: [$strokeColor, 'transparent', 'transparent', 'transparent', 'transparent', 'transparent'],
            connectorColors: 'transparent'
          }
        }
      },
      fill: {
        type: 'gradient',
        gradient: {
          shade: 'dark',
          gradientToColors: [window.colors.solid.primary, window.colors.solid.info],
          shadeIntensity: 1,
          type: 'horizontal',
          opacityFrom: 1,
          opacityTo: 1,
          stops: [0, 100, 100, 100]
        }
      },
      markers: {
        size: 0
      },
      legend: {
        show: false
      },
      labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
      dataLabels: {
        background: {
          foreColor: [$strokeColor, $strokeColor, $strokeColor, $strokeColor, $strokeColor, $strokeColor]
        }
      },
      yaxis: {
        show: false
      },
      grid: {
        show: false,
        padding: {
          bottom: -27
        }
      }
    };
    // salesVisitChart = new ApexCharts($salesVisitChart, salesVisitChartOptions);
    // salesVisitChart.render();
  });
